package gr.project.wishlist.controller;


import gr.project.wishlist.domain.dto.gift.GiftRequest;
import gr.project.wishlist.domain.dto.gift.GiftResponse;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.mapper.GiftMapper;
import gr.project.wishlist.service.GiftService;
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
@RequestMapping("/gift")
@RequiredArgsConstructor
public class GiftController {

    private final GiftMapper giftMapper;

    private final GiftService giftService;


    @PostMapping
    public GiftResponse create(@RequestBody @Valid GiftRequest request) {
        Gift entity = giftMapper.toEntity(request);
        Gift createdGift = giftService.create(entity, request.listId());
        return giftMapper.toResponse(createdGift);
    }

    @PutMapping("/{id}")
    public GiftResponse update(@PathVariable Long id, @RequestBody @Valid GiftRequest request) {
        Gift createdGift = giftService.update(request, id);
        return giftMapper.toResponse(createdGift);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        giftService.delete(id);
    }

    @GetMapping("/wishlist/{id}")
    public List<GiftResponse> readAllGifts(@PathVariable Long id) {
        List<Gift> gifts = giftService.getGiftsByWishlistId(id);
        return giftMapper.toResponse(gifts);
    }
}
