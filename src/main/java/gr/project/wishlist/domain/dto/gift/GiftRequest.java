package gr.project.wishlist.domain.dto.gift;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на создание нового подарка")
public record GiftRequest(

        @Schema(description = "Название подарка")
        @NotBlank(message = "Название не может быть пустым")
        @Size(max = 255, message = "Длина не может быть больше 255 символов")
        String title,

        @Schema(description = "Детали подарка")
        @Size(max = 1000, message = "Длина не может быть больше 1000 символов")
        String details,

        @Size(max = 1000, message = "Длина не может быть больше 1000 символов")
        @Schema(description = "Ссылка на подарок")
        String link,

        @Schema(description = "Id списка")
        Long listId
) {
}
