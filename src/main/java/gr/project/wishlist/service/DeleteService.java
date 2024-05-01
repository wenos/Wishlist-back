package gr.project.wishlist.service;

import gr.project.wishlist.config.SuperUserConfig;
import gr.project.wishlist.domain.dto.user.DeleteUserRequest;
import gr.project.wishlist.exception.user.ForbiddenAccessProblem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
@RequiredArgsConstructor
public class DeleteService {
    private final UserService userService;
    private final WishlistService wishlistService;
    private final SuperUserConfig superUserConfig;


    /**
     * Удаление пользователя
     *
     * @param request Запрос на удаление пользователя
     */
    public void deleteUser(DeleteUserRequest request) {
        // Проверяем если права на удаление пользователя
        var currentUser = userService.getCurrentUser();
        var userForDelete = userService.getById(request.userId());

        // Если это не сам пользователь, проверяем
        if (!currentUser.getId().equals(userForDelete.getId())) {

            // Если не админ, то кидаем ошибку
            if (!currentUser.isAdmin()) {
                throw new ForbiddenAccessProblem();
            }

            // Если админ, то проверяем, что удаляемый пользователь не админ
            if (!userService.hasAccessToUser(currentUser, userForDelete) && !superUserConfig.isSuperuser(currentUser.getId())) {
                throw new ForbiddenAccessProblem();
            }
        }

        // Суперпользователь не может удалиться
        if (superUserConfig.isSuperuser(userForDelete.getId())) {
            throw new ForbiddenAccessProblem();
        }

        if (request.deleteComments()) {
            wishlistService.deleteByAuthorId(userForDelete.getId());
            userForDelete.setWishlists(null);
        }


        userForDelete.setDeleted(OffsetDateTime.now());
        userService.save(userForDelete);
    }
}
