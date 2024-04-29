package gr.project.wishlist.controller;


import gr.project.wishlist.domain.dto.giftlist.WishlistRequest;
import gr.project.wishlist.domain.dto.giftlist.WishlistResponse;
import gr.project.wishlist.domain.model.Wishlist;
import gr.project.wishlist.mapper.WishlistMapper;
import gr.project.wishlist.service.WishlistService;
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

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
public class WishlistController {

    private final WishlistMapper wishlistMapper;

    private final WishlistService wishlistService;



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

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Long id) {
        wishlistService.delete(id);
    }
}
