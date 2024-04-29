package gr.project.wishlist.controller;


import gr.project.wishlist.domain.dto.booking.BookingResponse;
import gr.project.wishlist.domain.model.Booking;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.mapper.BookingMapper;
import gr.project.wishlist.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;
    private final BookingMapper bookingMapper;

    @PostMapping("/book/{uuid}/{giftId}")
    public void book(@PathVariable UUID uuid, @PathVariable Long giftId) {
        bookingService.book(uuid, giftId);
    }

    @PostMapping("/unbook/{giftId}")
    public void unbook(@PathVariable Long giftId) {
        bookingService.unbook(giftId);
    }

    @GetMapping
    public BookingResponse getBookings() {
        List<Booking> bookings = bookingService.getBookings();
        List<Gift> gifts = bookings.stream().map(Booking::getGift).toList();
        return bookingMapper.toResponse(gifts);
    }
}
