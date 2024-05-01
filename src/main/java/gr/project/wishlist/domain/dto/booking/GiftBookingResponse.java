package gr.project.wishlist.domain.dto.booking;

import gr.project.wishlist.domain.utils.BookingStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record GiftBookingResponse(
        @Schema(description = "Id подарка")
        Long id,

        @Schema(description = "Наименование подарка")
        String title,

        @Schema(description = "Детали подарка")
        String details,

        @Schema(description = "Ссылка на подарок")
        String link,

        @Schema(description = "Статус подарка")
        BookingStatus status
) {
}
