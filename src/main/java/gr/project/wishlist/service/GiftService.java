package gr.project.wishlist.service;

import gr.project.wishlist.domain.dto.gift.GiftRequest;
import gr.project.wishlist.domain.model.BookingStatus;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.domain.model.Wishlist;
import gr.project.wishlist.exception.gift.GiftNotFoundProblem;
import gr.project.wishlist.exception.user.ForbiddenAccessProblem;
import gr.project.wishlist.repository.GiftRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GiftService {

    private final GiftRepository giftRepository;
    private final WishlistService wishlistService;
    private final UserService userService;


    public Gift save(Gift gift) {
        return giftRepository.save(gift);
    }

    public Gift create(Gift gift, Long wishlistId) {
        Wishlist wishlist = wishlistService.getById(wishlistId);
        checkUser(wishlist);
        gift.setWishlist(wishlist);
        gift.setStatus(BookingStatus.NOT_BOOKED);
        return save(gift);
    }

    public Optional<Gift> findById(Long id) {
        return giftRepository.findById(id);
    }

    public Gift getById(Long id) {
        return findById(id).orElseThrow(() -> new GiftNotFoundProblem(id));
    }

    public List<Gift> getAll() {
        return giftRepository.findAll();
    }

    public List<Gift> getGiftsByWishlistId(Long wishlistId) {
        return giftRepository.findGiftsByWishlistId(wishlistId);
    }

    public Gift update(GiftRequest request, Long id) {
        Gift foundGift = getById(id);
        checkUser(foundGift.getWishlist());
        return simpleUpdate(foundGift, request);
    }

    public Gift simpleUpdate(Gift gift, GiftRequest request) {
        gift.setLink(request.link());
        gift.setDetails(request.details());
        gift.setTitle(request.title());
        return giftRepository.save(gift);
    }

    public void delete(Long giftId) {
        Gift foundGift = getById(giftId);
        checkUser(foundGift.getWishlist());
        giftRepository.deleteById(giftId);
    }

    public void simpleDelete(Long giftId) {
        giftRepository.deleteById(giftId);
    }


    private void checkUser(Wishlist wishlist) {
        Long userId = userService.getCurrentUser().getId();

        if (!wishlist.getOwner().getId().equals(userId)) {
            throw new ForbiddenAccessProblem();
        }
    }

}


