package gr.project.wishlist.service;


import gr.project.wishlist.domain.dto.gift.GiftRequest;
import gr.project.wishlist.domain.utils.AccessMode;
import gr.project.wishlist.domain.utils.BookingStatus;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.domain.model.SharedAccess;
import gr.project.wishlist.domain.model.Wishlist;
import gr.project.wishlist.exception.link.AccessModeProblem;
import gr.project.wishlist.exception.user.ForbiddenAccessProblem;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.Pair;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LinkGiftService {
    private final LinkService linkService;
    private final GiftService giftService;


    public Gift createGiftWithLink(UUID uuid, Gift gift) {
        Wishlist wishlist = checkAccessAndGetWishlist(uuid);
        gift.setWishlist(wishlist);
        gift.setStatus(BookingStatus.NOT_BOOKED);
        return giftService.save(gift);
    }

    public Gift updateGiftWithLink(UUID uuid, Long giftId, GiftRequest request) {
        Wishlist wishlist = checkAccessAndGetWishlist(uuid);
        Gift foundGift = giftService.getById(giftId);
        checkWishlistsIds(wishlist, foundGift.getWishlist());
        return giftService.simpleUpdate(foundGift, request);
    }

    public void deleteGiftWithLink(UUID uuid, Long giftId) {
        Wishlist wishlist = checkAccessAndGetWishlist(uuid);
        Gift foundGift = giftService.getById(giftId);
        checkWishlistsIds(wishlist, foundGift.getWishlist());
        giftService.simpleDelete(giftId);
    }

    public Pair<Wishlist, List<Gift>> getGiftsWithLink(UUID uuid) {
        SharedAccess sharedAccess = linkService.getById(uuid);
        return new Pair<>(sharedAccess.getWishlist(), sharedAccess.getWishlist().getGifts());
    }

    private Wishlist checkAccessAndGetWishlist(UUID uuid) {
        SharedAccess sharedAccess = linkService.getById(uuid);
        if (sharedAccess.getAccessMode() != AccessMode.EDIT_MODE) {
            throw new AccessModeProblem();
        }
        return sharedAccess.getWishlist();
    }

    public void checkWishlistsIds(Wishlist wishlist, Wishlist otherWishlist) {
        Long wishlistId = wishlist.getId();
        Long otherWishlistId = otherWishlist.getId();
        if (!wishlistId.equals(otherWishlistId)) {
            throw new ForbiddenAccessProblem();
        }
    }
}
