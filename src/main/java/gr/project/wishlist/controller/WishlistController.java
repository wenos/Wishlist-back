package gr.project.wishlist.controller;


import gr.project.wishlist.domain.dto.gift.GiftResponse;
import gr.project.wishlist.domain.dto.giftlist.WishlistRequest;
import gr.project.wishlist.domain.dto.giftlist.WishlistResponse;
import gr.project.wishlist.domain.dto.link.LinkRequest;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.domain.model.SharedAccess;
import gr.project.wishlist.domain.model.Wishlist;
import gr.project.wishlist.mapper.GiftMapper;
import gr.project.wishlist.mapper.WishlistMapper;
import gr.project.wishlist.service.GiftService;
import gr.project.wishlist.service.LinkService;
import gr.project.wishlist.service.WishlistService;
import jakarta.annotation.Nullable;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistMapper wishlistMapper;
    private final GiftMapper giftMapper;
    private final WishlistService wishlistService;
    private final GiftService giftService;
    private final LinkService linkService;


    @PostMapping
    public WishlistResponse create(@RequestBody @Valid WishlistRequest request) {
        Wishlist entity = wishlistMapper.toEntity(request);
        Wishlist createdWishlist = wishlistService.create(entity);
        return wishlistMapper.toResponse(createdWishlist);
    }

    @GetMapping
    public List<WishlistResponse> readAll() {
        List<Wishlist> list = wishlistService.getAll();
        return wishlistMapper.toResponse(list);
    }

    @PutMapping("/{id}")
    public WishlistResponse updateById(@PathVariable Long id, @RequestBody @Valid WishlistRequest request) {
        Wishlist wishlist = wishlistService.update(id, request);
        return wishlistMapper.toResponse(wishlist);
    }


    @GetMapping("/{id}")
    public WishlistResponse readById(@PathVariable Long id) {
        Wishlist wishlist = wishlistService.getById(id);
        return wishlistMapper.toResponse(wishlist);
    }


    @GetMapping("/my/{id}/gifts")
    public List<GiftResponse> readAllGifts(@PathVariable Long id) {
        List<Gift> gifts = giftService.getGiftsByWishlistId(id);
        return giftMapper.toResponse(gifts);
    }

    @GetMapping("/{uuid}/gifts")
    public List<GiftResponse> readAllGifts(@PathVariable UUID uuid) {
        SharedAccess sharedAccess = linkService.getById(uuid);
        List<Gift> gifts = giftService.getGiftsByWishlistId(sharedAccess.getWishlist().getId());
        return giftMapper.toResponse(gifts);
    }

    @GetMapping("/link")
    public URL createOrGetLink(@RequestBody @Valid LinkRequest request) throws MalformedURLException {
        return linkService.createOrGet(request);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        wishlistService.delete(id);
    }

}
