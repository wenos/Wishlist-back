package gr.project.wishlist.domain.dto.user;

import gr.project.wishlist.domain.utils.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.OffsetDateTime;

@Builder
@Schema(description = "Ответ на запрос пользователя")
public record UserResponse(

        @Schema(description = "Идентификатор пользователя", example = "1")
        Long id,

        @Schema(description = "Логин пользователя", example = "ivanov")
        String username,

        @Schema(description = "Почта пользователя", example = "example@tinkoff.ru")
        String email,

        @Schema(description = "Роль пользователя", example = "ROLE_USER")
        Role role,

        @Schema(description = "Дата окончания бана", example = "2021-10-10T10:10:10+03:00")
        OffsetDateTime banned,

        @Schema(description = "Дата удаления пользователя", example = "2021-10-10T10:10:10+03:00")
        OffsetDateTime deleted
) {
}