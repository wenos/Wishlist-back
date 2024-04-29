package gr.project.wishlist.controller;


import gr.project.wishlist.domain.dto.gift.GiftRequest;
import gr.project.wishlist.domain.dto.gift.GiftResponse;
import gr.project.wishlist.domain.dto.link.LinkRequest;
import gr.project.wishlist.domain.dto.link.LinkResponse;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.domain.model.SharedAccess;
import gr.project.wishlist.mapper.GiftMapper;
import gr.project.wishlist.mapper.SharedAccessMapper;
import gr.project.wishlist.service.LinkGiftService;
import gr.project.wishlist.service.LinkService;
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
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class LinkController {

    private final LinkGiftService linkGiftService;
    private final LinkService linkService;
    private final GiftMapper giftMapper;
    private final SharedAccessMapper sharedAccessMapper;

    @PostMapping("/shared/{uuid}")
    public GiftResponse create(@PathVariable UUID uuid, @RequestBody @Valid GiftRequest request) {
        Gift entity = giftMapper.toEntity(request);
        Gift gift = linkGiftService.createGiftWithLink(uuid, entity);
        return giftMapper.toResponse(gift);
    }

    @GetMapping("/shared/{uuid}")
    public List<GiftResponse> get(@PathVariable UUID uuid) {
        Set<Gift> gifts = linkGiftService.getGiftsWithLink(uuid);
        return giftMapper.toResponse(gifts);
    }

    @PutMapping("/shared/{uuid}/{giftId}")
    public GiftResponse update(@PathVariable UUID uuid, @PathVariable Long giftId, @RequestBody @Valid GiftRequest request) {
        Gift gift = linkGiftService.updateGiftWithLink(uuid, giftId, request);
        return giftMapper.toResponse(gift);
    }

    @DeleteMapping("/shared/{uuid}/{giftId}")
    public void delete(@PathVariable UUID uuid, @PathVariable Long giftId) {
        linkGiftService.deleteGiftWithLink(uuid, giftId);
    }

    @PostMapping("/link")
    public LinkResponse createOrGetLink(@RequestBody @Valid LinkRequest request) throws MalformedURLException {
        SharedAccess sharedAccess = linkService.createOrGet(request);
        return sharedAccessMapper.toResponse(sharedAccess);
    }
}
