package gr.project.wishlist.domain.dto.booking;

import java.util.List;

public record BookingWishlist(
        Long id,
        String title,
        String description,
        List<GiftBookingResponse> gifts
) {
}
