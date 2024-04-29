package gr.project.wishlist.mapper;

import gr.project.wishlist.domain.dto.booking.BookingResponse;
import gr.project.wishlist.domain.dto.booking.GiftBookingResponse;
import gr.project.wishlist.domain.dto.booking.WishlistBookingResponse;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.domain.model.Wishlist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BookingMapper {
    public BookingResponse toResponse(List<Gift> gifts) {
        Map<WishlistBookingResponse, List<GiftBookingResponse>> map = new HashMap<>();

        gifts.forEach(
                gift -> {
                    Wishlist wishlist = gift.getWishlist();
                    WishlistBookingResponse wishlistBookingResponse = new WishlistBookingResponse(wishlist.getId(), wishlist.getTitle(), wishlist.getOwner().getUsername());
                    GiftBookingResponse giftBookingResponse =
                            new GiftBookingResponse(
                                    gift.getId(),
                                    gift.getTitle(),
                                    gift.getDetails(),
                                    gift.getLink(),
                                    gift.getStatus()
                            );
                    if (!map.containsKey(wishlistBookingResponse)) {
                        map.put(wishlistBookingResponse, new ArrayList<>());
                    }

                    List<GiftBookingResponse> giftsForWishlist = map.get(wishlistBookingResponse);
                    giftsForWishlist.add(giftBookingResponse);

                }
        );
        return new BookingResponse(map);
    }
}
