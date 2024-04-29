package gr.project.wishlist.domain.dto.giftlist;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Ответ на запрос списка")
public record WishlistResponse(
        @Schema(description = "Id списка")
        Long id,
        @Schema(description = "Наименование списка")
        String title,
        @Schema(description = "Описание списка")
        String description
) {
}
