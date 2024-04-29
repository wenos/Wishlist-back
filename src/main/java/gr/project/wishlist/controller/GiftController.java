package gr.project.wishlist.controller;


import gr.project.wishlist.domain.dto.gift.GiftRequest;
import gr.project.wishlist.domain.dto.gift.GiftResponse;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.mapper.GiftMapper;
import gr.project.wishlist.service.GiftService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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


    @GetMapping("/{id}")
    public GiftResponse read(@PathVariable Long id) {
        Gift gift = giftService.getById(id);
        return giftMapper.toResponse(gift);
    }


    @GetMapping
    public List<GiftResponse> readAll() {
        List<Gift> gifts = giftService.getAll();
        return giftMapper.toResponse(gifts);
    }

}
