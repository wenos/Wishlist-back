package gr.project.wishlist.mapper;

import gr.project.wishlist.domain.dto.booking.BookingResponse;
import gr.project.wishlist.domain.dto.booking.BookingWishlist;
import gr.project.wishlist.domain.dto.booking.GiftBookingResponse;
import gr.project.wishlist.domain.dto.booking.WishlistBookingResponse;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.domain.model.Wishlist;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class BookingMapper {

    private final GiftMapper giftMapper;

    public BookingResponse toResponse(List<Gift> gifts) {
        Map<Long, WishlistBookingResponse> map = new HashMap<>();
        gifts.forEach(
                gift -> {
                    Wishlist wishlist = gift.getWishlist();
                    if (!map.containsKey(wishlist.getId())) {
                        WishlistBookingResponse wishlistBookingResponse = new WishlistBookingResponse(wishlist.getId(), wishlist.getOwner().getUsername(), wishlist.getTitle(), new ArrayList<>());
                        map.put(wishlist.getId(), wishlistBookingResponse);
                    }
                    GiftBookingResponse giftBookingResponse =
                            new GiftBookingResponse(
                                    gift.getId(),
                                    gift.getTitle(),
                                    gift.getDetails(),
                                    gift.getLink(),
                                    gift.getStatus()
                            );
                    List<GiftBookingResponse> giftsForWishlist = map.get(wishlist.getId()).gifts();
                    giftsForWishlist.add(giftBookingResponse);
                }
        );
        return new BookingResponse(map.values().stream().toList());
    }

    public BookingWishlist toResponse(Pair<Wishlist, List<Gift>> wishlistSetGiftsPair ) {

        return new BookingWishlist(
                wishlistSetGiftsPair.a.getId(),
                wishlistSetGiftsPair.a.getTitle(),
                wishlistSetGiftsPair.a.getDescription(),
                wishlistSetGiftsPair.b.stream().map(
                        gift -> new GiftBookingResponse(
                                gift.getId(),
                                gift.getTitle(),
                                gift.getDetails(),
                                gift.getLink(),
                                gift.getStatus()
                        )
                ).toList()
        );
    }
}
