package gr.project.wishlist.service;


import gr.project.wishlist.config.LinkConfig;
import gr.project.wishlist.domain.dto.link.LinkRequest;
import gr.project.wishlist.domain.model.AccessMode;
import gr.project.wishlist.domain.model.Wishlist;
import gr.project.wishlist.domain.model.SharedAccess;
import gr.project.wishlist.exception.link.LinkNotFoundProblem;
import gr.project.wishlist.repository.LinkRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import static java.util.Objects.isNull;

@Service
@RequiredArgsConstructor
public class LinkService {

    private final LinkConfig linkConfig;
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
            sharedAccess.setAccessMode(AccessMode.VIEW_MODE);
        }
        return save(sharedAccess);
    }

    public URL createOrGet(LinkRequest linkRequest) throws MalformedURLException {
        System.out.println(linkRequest.getAccessMode());
        SharedAccess sharedAccess = linkRepository.findByWishlistIdAndAccessMode(
                linkRequest.getWishlistId(),
                linkRequest.getAccessMode()
        ).orElseGet(() -> create(linkRequest));

        return createLink(sharedAccess.getId());
    }


    public SharedAccess getById(UUID id) {
        return linkRepository.findById(id).orElseThrow(() -> new LinkNotFoundProblem(id));
    }


    public URL createLink(UUID id) throws MalformedURLException {
        return new URL(
                linkConfig.protocol,
                linkConfig.host,
                linkConfig.port,
                linkConfig.prefix + id.toString() + linkConfig.postfix);
    }
}
