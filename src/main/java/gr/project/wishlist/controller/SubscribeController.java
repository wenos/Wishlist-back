package gr.project.wishlist.controller;


import gr.project.wishlist.domain.dto.gift.GiftResponse;
import gr.project.wishlist.domain.dto.giftlist.WishlistSubscriptionResponse;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.domain.model.Wishlist;
import gr.project.wishlist.mapper.GiftMapper;
import gr.project.wishlist.mapper.WishlistMapper;
import gr.project.wishlist.service.SubscribeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class SubscribeController {
    private final SubscribeService subscribeService;
    private final WishlistMapper wishlistMapper;
    private final GiftMapper giftMapper;

    @PostMapping("/subscribe/{uuid}")
    public void subscribe(@PathVariable UUID uuid) {
        subscribeService.subscribe(uuid);
    }

    @PostMapping("/unsubscribe/{wishlistId}")
    public void unsubscribe(@PathVariable Long wishlistId) {
        subscribeService.unsubscribe(wishlistId);
    }

    @GetMapping("/subscriptions")
    public List<WishlistSubscriptionResponse> subscriptions() {
        List<Wishlist> wishlists = subscribeService.subscriptions();
        return wishlistMapper.toWishlistSubscriptionResponse(wishlists);
    }

    @GetMapping("/subscriptions/{id}/gifts")
    public List<GiftResponse> gifts(@PathVariable Long id) {
        List<Gift> gifts = subscribeService.getGifts(id);
        return giftMapper.toResponse(gifts);
    }


}