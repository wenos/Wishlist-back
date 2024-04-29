package gr.project.wishlist.service;


import gr.project.wishlist.domain.dto.link.LinkRequest;
import gr.project.wishlist.domain.model.AccessMode;
import gr.project.wishlist.domain.model.SharedAccess;
import gr.project.wishlist.domain.model.Wishlist;
import gr.project.wishlist.exception.link.LinkNotFoundProblem;
import gr.project.wishlist.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkRepository linkRepository;
    private final WishlistService wishlistService;

    public SharedAccess save(SharedAccess sharedAccess) {
        return linkRepository.save(sharedAccess);
    }

    public SharedAccess create(LinkRequest request) {
        Wishlist wishlist = wishlistService.getById(request.getWishlistId());
        SharedAccess sharedAccess = SharedAccess.builder()
                .wishlist(wishlist)
                .accessMode(request.getAccessMode())
                .build();
        if (isNull(request.getAccessMode())) {
            sharedAccess.setAccessMode(AccessMode.BOOKING_MODE);
        }
        return save(sharedAccess);
    }

    public SharedAccess createOrGet(LinkRequest linkRequest) {
        System.out.println(linkRequest.getAccessMode());

        return linkRepository.findByWishlistIdAndAccessMode(
                linkRequest.getWishlistId(),
                linkRequest.getAccessMode()
        ).orElseGet(() -> create(linkRequest));
    }


    public SharedAccess getById(UUID id) {
        return linkRepository.findById(id).orElseThrow(() -> new LinkNotFoundProblem(id));
    }
}
