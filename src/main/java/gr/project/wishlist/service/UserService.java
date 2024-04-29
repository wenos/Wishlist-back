package gr.project.wishlist.service;

import gr.project.wishlist.config.SuperUserConfig;
import gr.project.wishlist.domain.dto.user.UpdateUserPasswordRequest;
import gr.project.wishlist.domain.dto.user.UpdateUserRoleRequest;
import gr.project.wishlist.domain.dto.user.UserBanRequest;
import gr.project.wishlist.domain.dto.user.UserFilter;
import gr.project.wishlist.domain.model.Role;
import gr.project.wishlist.domain.model.User;
import gr.project.wishlist.exception.user.ForbiddenAccessProblem;
import gr.project.wishlist.exception.user.InvalidUserPasswordProblem;
import gr.project.wishlist.exception.user.UserNotFound;
import gr.project.wishlist.exception.user.UserNotFoundProblem;
import gr.project.wishlist.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SuperUserConfig superUserConfig;


    public User save(User user) {
        return userRepository.save(user);
    }

    public User create(User user) {
        return save(user);
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(() -> new UserNotFound(username));
    }

    public User getCurrentUser() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return getByUsername(name);
    }

    /**
     * Создание суперпользователя, если его нет
     */
    @PostConstruct
    public void initSuperUser() {
        if (!superUserConfig.isSuperuserEnabled()) {
            return;
        }
        var superuserId = superUserConfig.getSuperuserId();
        var user = userRepository.findById(superuserId);

        if (user.isEmpty()) {
            User u = new User();
            u.setId(superuserId);
            u.setUsername("superuser");
            u.setEmail(RandomStringUtils.randomAlphanumeric(10) + "@gmail.ru");
            u.setPassword(passwordEncoder.encode(superUserConfig.getSuperuserDefaultPassword()));
            u.setRole(Role.ROLE_ADMIN);
            userRepository.save(u);
            log.info("Создан суперпользователь с именем пользователя superuser и паролем superuser");
        } else {
            if (!user.get().isAdmin()) {
                user.get().setRole(Role.ROLE_ADMIN);
                userRepository.save(user.get());
                log.info("Пользователь с ID {} теперь является суперпользователем", superuserId);
            }
        }
    }

    public Optional<User> findById(Long userId) {
        return userRepository.findById(userId);
    }


    public User getById(Long userId) {
        return findById(userId).orElseThrow(UserNotFoundProblem::new);
    }

    public boolean isSuperuser() {
        var user = getCurrentUser();
        return superUserConfig.isSuperuser(user.getId());
    }

    /**
     * Получение по фильтру
     *
     * @param filter фильтр
     * @return Найденные пользователи
     */
    public Page<User> findByFilter(UserFilter filter) {
        Pageable pageable = PageRequest.of(filter.getPage(), filter.getSize());

        return userRepository.findAllWithFilter(
                filter.getId(),
                filter.getUsername(),
                filter.getEmail(),
                Optional.ofNullable(filter.getRole()).map(Role::valueOf).orElse(null),
                filter.getIsBanned(),
                OffsetDateTime.now(),
                pageable
        );
    }

    /**
     * Изменить роль пользователя
     *
     * @param request Запрос на изменение роли
     */
    public void changeRole(UpdateUserRoleRequest request) {
        User currentUser = getCurrentUser();
        User user = getById(request.id());

        if (!hasAccessToUser(currentUser, user)) {
            throw new ForbiddenAccessProblem();
        }

        user.setRole(Role.valueOf(request.role()));
        save(user);
    }

    public boolean hasAccessToUser(User currentUser, User user) {
        /*
            Права доступа:
            Суперпользователь - Админ - да
            Суперпользователь - Пользователь - да

            Админ - Админ - нет
            Админ - Пользователь - да

            Управлять собой - нет
         */
        if (currentUser.getId().equals(user.getId())) {
            return false;
        }

        if (superUserConfig.isSuperuser(currentUser.getId())) {
            return true;
        }

        // Проверка никогда не должна сработать, но предосторожность не помешает
        if (superUserConfig.isSuperuser(user.getId())) {
            return false;
        }

        if (currentUser.isAdmin()) {
            return !user.getRole().equals(Role.ROLE_ADMIN);
        }

        return false;
    }

    public void setBannedAt(UserBanRequest request) {
        User currentUser = getCurrentUser();
        User user = getById(request.id());

        if (!hasAccessToUser(currentUser, user)) {
            throw new ForbiddenAccessProblem();
        }

        user.setBannedAt(OffsetDateTime.now()
                .plusDays(Optional.ofNullable(request.days()).orElse(0))
                .plusHours(Optional.ofNullable(request.hours()).orElse(0))
                .plusMinutes(Optional.ofNullable(request.minutes()).orElse(0)));

        save(user);
    }

    /**
     * Сбросить дату блокировки пользователя
     *
     * @param userId Идентификатор пользователя для сброса блокировки
     */
    public void resetBannedAt(Long userId) {
        User currentUser = getCurrentUser();
        User user = getById(userId);

        if (!hasAccessToUser(currentUser, user)) {
            throw new ForbiddenAccessProblem();
        }

        user.setBannedAt(null);
        save(user);
    }


    /**
     * Изменить пароль пользователя
     *
     * @param request Запрос на изменение пароля
     */
    public void changePassword(UpdateUserPasswordRequest request) {
        User currentUser = getCurrentUser();

        if (!passwordEncoder.matches(request.oldPassword(), currentUser.getPassword())) {
            throw new InvalidUserPasswordProblem();
        }

        currentUser.setPassword(passwordEncoder.encode(request.newPassword()));
        save(currentUser);
    }
}
