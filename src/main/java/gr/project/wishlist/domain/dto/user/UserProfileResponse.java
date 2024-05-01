package gr.project.wishlist.domain.dto.user;

import gr.project.wishlist.domain.utils.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
@Schema(description = "Ответ на запрос пользователя")
public record UserProfileResponse(
        @Schema(description = "Идентификатор пользователя", example = "1")
        Long id,

        @Schema(description = "Логин пользователя", example = "ivanov")
        String username,


        @Schema(description = "Почта пользователя", example = "example@tinkoff.ru")
        String email,


        @Schema(description = "Дата окончания бана", example = "example@tinkoff.ru")
        OffsetDateTime banned,


        @Schema(description = "Роль пользователя", example = "ROLE_USER")
        Role role,

        @Schema(description = "Настоящее имя пользователя", example = "Иванов Иван Иванович")
        String realName,

        @Schema(description = "Обо мне", example = "Я банан")
        String about
) {
}