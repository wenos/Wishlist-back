package gr.project.wishlist.service;


import gr.project.wishlist.domain.dto.giftlist.WishlistRequest;
import gr.project.wishlist.domain.model.User;
import gr.project.wishlist.domain.model.Wishlist;
import gr.project.wishlist.exception.giftlist.WishlistNotFoundProblem;
import gr.project.wishlist.exception.user.ForbiddenAccessProblem;
import gr.project.wishlist.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class WishlistService {

    private final WishlistRepository wishlistRepository;
    private final UserService userService;

    public Wishlist save(Wishlist list) {
        return wishlistRepository.save(list);
    }

    public Wishlist create(Wishlist list) {
        User owner = userService.getCurrentUser();
        list.setOwner(owner);
        return save(list);
    }
    public Wishlist update(Long id, WishlistRequest list) {
        Wishlist wishlist = getById(id);
        checkUser(wishlist);
        wishlist.setTitle(list.title());
        wishlist.setDescription(list.description());
        return save(wishlist);
    }

    public void delete(Long id) {
        Wishlist wishlist = getById(id);
        checkUser(wishlist);
        wishlist.getGifts().forEach(gift -> gift.setWishlist(null));
        wishlist.getSharedAccess().forEach(sharedAccess -> sharedAccess.setWishlist(null));
        wishlist.getSubscribers().forEach(subscribe -> subscribe.setWishlist(null));
        wishlistRepository.deleteById(id);
    }

    public List<Wishlist> getAll() {
        User owner = userService.getCurrentUser();
        return wishlistRepository.findAllByOwnerId(owner.getId());
    }

    public Optional<Wishlist> findById(Long id) {
        return wishlistRepository.findById(id);
    }

    public Wishlist getById(Long id) {
        return findById(id).orElseThrow(() -> new WishlistNotFoundProblem(id));
    }


    @Transactional
    public void deleteByAuthorId(Long id) {
        wishlistRepository.deleteAllByOwnerId(id);
    }


    private void checkUser(Wishlist wishlist) {
        Long userId = userService.getCurrentUser().getId();

        if (!wishlist.getOwner().getId().equals(userId)) {
            throw new ForbiddenAccessProblem();
        }
    }
}
