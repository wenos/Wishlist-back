package gr.project.wishlist.controller;


import gr.project.wishlist.domain.dto.gift.GiftRequest;
import gr.project.wishlist.domain.dto.gift.GiftResponse;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.mapper.GiftMapper;
import gr.project.wishlist.service.LinkGiftService;
import gr.project.wishlist.service.SubscribeService;
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
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shared")
public class LinkController {

    private final LinkGiftService linkGiftService;
    private final GiftMapper mapper;

    @PostMapping("/{uuid}")
    public GiftResponse create(@PathVariable UUID uuid, @RequestBody @Valid GiftRequest request) {
        Gift entity = mapper.toEntity(request);
        Gift gift = linkGiftService.createGiftWithLink(uuid, entity);
        return mapper.toResponse(gift);
    }

    @GetMapping("/{uuid}")
    public List<GiftResponse> get(@PathVariable UUID uuid) {
        Set<Gift> gifts = linkGiftService.getGiftsWithLink(uuid);
        return mapper.toResponse(gifts);
    }

    @PutMapping("/{uuid}/{giftId}")
    public GiftResponse update(@PathVariable UUID uuid, @PathVariable Long giftId, @RequestBody @Valid GiftRequest request) {
        Gift gift = linkGiftService.updateGiftWithLink(uuid, giftId, request);
        return mapper.toResponse(gift);
    }

    @DeleteMapping("/{uuid}/{giftId}")
    public void delete(@PathVariable UUID uuid, @PathVariable Long giftId) {
        linkGiftService.deleteGiftWithLink(uuid, giftId);
    }
}
