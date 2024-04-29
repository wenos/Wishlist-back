package gr.project.wishlist.service;


import gr.project.wishlist.domain.model.AccessMode;
import gr.project.wishlist.domain.model.Booking;
import gr.project.wishlist.domain.model.BookingId;
import gr.project.wishlist.domain.model.BookingStatus;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.domain.model.SharedAccess;
import gr.project.wishlist.domain.model.User;
import gr.project.wishlist.exception.gift.GiftBookedProblem;
import gr.project.wishlist.exception.link.AccessModeProblem;
import gr.project.wishlist.exception.user.ForbiddenAccessProblem;
import gr.project.wishlist.repository.BookingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BookingService {
    private final UserService userService;
    private final LinkService linkService;
    private final GiftService giftService;
    private final BookingRepository repository;

    @Transactional
    public void book(UUID uuid, Long giftId) {
        SharedAccess sharedAccess = linkService.getById(uuid);
        if (sharedAccess.getAccessMode() != AccessMode.BOOKING_MODE) {
            throw new AccessModeProblem();
        }
        Gift foundGift = giftService.getById(giftId);
        if (foundGift.getStatus() != BookingStatus.NOT_BOOKED) {
            throw new GiftBookedProblem(giftId);
        }
        if (!sharedAccess.getWishlist().getId().equals(foundGift.getWishlist().getId())) {
            throw new ForbiddenAccessProblem();
        }
        User user = userService.getCurrentUser();
        foundGift.setStatus(BookingStatus.BOOKED);
        BookingId id = new BookingId(giftId, user.getId());
        repository.save(new Booking(id, foundGift, user));
        giftService.save(foundGift);
    }


    @Transactional
    public void unbook(Long giftId){
        User user = userService.getCurrentUser();
        BookingId id = new BookingId(giftId, user.getId());
        repository.deleteById(id);
        Gift foundGift = giftService.getById(giftId);
        foundGift.setStatus(BookingStatus.BOOKED);
        giftService.save(foundGift);
    }

    public List<Booking> getBookings() {
        User user = userService.getCurrentUser();
        return repository.findAllByUserId(user.getId());
    }

}
