package gr.project.wishlist.domain.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на обновление информации о пользователе")
public record UpdateUserInfoRequest(
        @Schema(description = "Настоящее имя пользователя", example = "Иван")
        @Size(max = 60, message = "Настоящее имя пользователя должно быть до 60 символов")
        String realName,

        @Schema(description = "Обо мне", example = "Обо мне")
        @Size(max = 500, message = "Обо мне должно быть не более 500 символов")
        String about
) {
}