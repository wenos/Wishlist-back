package gr.project.wishlist.domain.dto.booking;

import java.util.List;
import java.util.Map;

public record BookingResponse(
        Map<WishlistBookingResponse, List<GiftBookingResponse>> bookingResponseListMap
) {
}
