package gr.project.wishlist.domain.dto.gift;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на запрос подарка")
public record GiftResponse(
        @Schema(description = "Id подарка")
        Long id,

        @Schema(description = "Наименование подарка")
        String title,

        @Schema(description = "Детали подарка")
        String details,

        @Schema(description = "Ссылка на подарок")
        String link

) {
}
