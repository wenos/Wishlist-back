package gr.project.wishlist.domain.dto.booking;

import java.util.List;

public record WishlistBookingResponse(
        Long id,
        String ownerName,
        String title,
        List<GiftBookingResponse> gifts
) {
}
