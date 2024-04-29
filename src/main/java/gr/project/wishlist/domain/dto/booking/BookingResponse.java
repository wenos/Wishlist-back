package gr.project.wishlist.domain.dto.booking;

import java.util.List;

public record BookingResponse(
        List<WishlistBookingResponse> data
) {
}
