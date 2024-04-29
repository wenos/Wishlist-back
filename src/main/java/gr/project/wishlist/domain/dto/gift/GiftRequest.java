package gr.project.wishlist.domain.dto.gift;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на создание нового подарка")
public record GiftRequest(

        @Schema(description = "Название подарка")
        @NotBlank(message = "Название не может быть пустым")
        String title,

        @Schema(description = "Детали подарка")
        String details,

        @Schema(description = "Ссылка на подарок")
        String link,

        @Schema(description = "Id списка")
        Long listId
        ) {
}
