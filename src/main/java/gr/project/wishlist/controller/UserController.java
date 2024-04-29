package gr.project.wishlist.controller;

import gr.project.wishlist.domain.dto.pagination.PageResponse;
import gr.project.wishlist.domain.dto.user.DeleteUserRequest;
import gr.project.wishlist.domain.dto.user.UpdateUserPasswordRequest;
import gr.project.wishlist.domain.dto.user.UpdateUserRoleRequest;
import gr.project.wishlist.domain.dto.user.UserBanRequest;
import gr.project.wishlist.domain.dto.user.UserFilter;
import gr.project.wishlist.domain.dto.user.UserResponse;
import gr.project.wishlist.domain.model.User;
import gr.project.wishlist.mapper.UserMapper;
import gr.project.wishlist.service.DeleteService;
import gr.project.wishlist.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Работа с пользователями")
@RequestMapping("/users")
public class UserController {
    private final UserService service;
    private final UserMapper mapper;
    private final DeleteService deleteService;

    /**
     * Получение пользователя по id
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Operation(summary = "Получение пользователя по id")
    @GetMapping("/{userId}")
    public UserResponse getById(@PathVariable Long userId) {
        User user = service.getById(userId);
        return mapper.toResponse(user);
    }


    @Operation(summary = "Получение данного пользователя")
    @GetMapping("/current")
    public UserResponse getCurrent() {
        User user = service.getCurrentUser();
        return mapper.toResponse(user);
    }


    /**
     * Проверка что пользователь суперпользователь
     */
    @Operation(summary = "Проверка что пользователь суперпользователь")
    @GetMapping("/is-superuser")
    public Boolean isSuperuser() {
        return service.isSuperuser();
    }


    /**
     * Пагинация пользователей по фильтру
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MODERATOR')")
    @Operation(summary = "Получение пользователей по фильтру")
    @PostMapping("/filter")
    public PageResponse<UserResponse> findUsersByFilter(@RequestBody @Valid UserFilter filter) {
        var result = new PageResponse<UserResponse>();

        var users = service.findByFilter(filter);
        result.setTotalPages(users.getTotalPages());
        result.setTotalSize(users.getTotalElements());
        result.setPageNumber(users.getNumber());
        result.setPageSize(users.getSize());
        result.setContent(mapper.toResponse(users.getContent()));
        return result;
    }




    /**
     * Изменение роли пользователя
     */
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Изменение роли пользователя")
    @PutMapping("/change-role")
    public void changeRole(@RequestBody @Valid UpdateUserRoleRequest request) {
        service.changeRole(request);
    }



    @Operation(summary = "Удаление пользователя")
    @PostMapping("/delete")
    public void deleteUser(@RequestBody @Valid DeleteUserRequest request) {
        deleteService.deleteUser(request);
    }

    /**
     * Выдача бана пользователю
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Выдача бана пользователю")
    @PutMapping("/ban")
    public void setBannedAt(@RequestBody @Valid UserBanRequest request) {
        service.setBannedAt(request);
    }

    /**
     * Снятие бана с пользователя
     */
    @PreAuthorize("hasAnyRole('ROLE_ADMIN')")
    @Operation(summary = "Снятие бана с пользователя")
    @PutMapping("/unban/{userId}")
    public void resetBannedAt(@PathVariable Long userId) {
        service.resetBannedAt(userId);
    }


    /**
     * Смена пароля пользователя
     */
    @Operation(summary = "Смена пароля пользователя")
    @PostMapping("/change-password")
    public void changePassword(@RequestBody @Valid UpdateUserPasswordRequest request) {
        service.changePassword(request);
    }
}
