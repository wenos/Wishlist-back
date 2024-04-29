package gr.project.wishlist.domain.dto.booking;

import gr.project.wishlist.domain.model.BookingStatus;

public record GiftBookingResponse(
        Long id,
        String title,
        String details,
        String link,
        BookingStatus status
) {
}
