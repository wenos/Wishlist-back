package gr.project.wishlist.service;


import gr.project.wishlist.domain.utils.AccessMode;
import gr.project.wishlist.domain.model.Gift;
import gr.project.wishlist.domain.model.SharedAccess;
import gr.project.wishlist.domain.model.Subscribe;
import gr.project.wishlist.domain.utils.SubscribeId;
import gr.project.wishlist.domain.model.User;
import gr.project.wishlist.domain.model.Wishlist;
import gr.project.wishlist.exception.link.AccessModeProblem;
import gr.project.wishlist.exception.subscribe.SubscriptionNotFoundProblem;
import gr.project.wishlist.repository.SubscribeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class SubscribeService {
    private final UserService userService;
    private final LinkService linkService;
    private final SubscribeRepository repository;


    public void subscribe(UUID uuid) {
        SharedAccess sharedAccess = linkService.getById(uuid);
        if (sharedAccess.getAccessMode() != AccessMode.SUBSCRIBE_MODE) {
            throw new AccessModeProblem();
        }
        Wishlist wishlist = sharedAccess.getWishlist();
        User user = userService.getCurrentUser();
        SubscribeId id = new SubscribeId(wishlist.getId(), user.getId());
        Subscribe subscribe = new Subscribe(id, wishlist, user);
        repository.save(subscribe);
    }

    public void unsubscribe(Long wishlistId) {
        User user = userService.getCurrentUser();
        SubscribeId id = new SubscribeId(wishlistId, user.getId());
        repository.deleteById(id);
    }

    public List<Wishlist> subscriptions() {
        User user = userService.getCurrentUser();
        List<Subscribe> subscriptions = repository.findAllByUserId(user.getId());
        return subscriptions.stream().map(Subscribe::getWishlist).toList();
    }

    public List<Gift> getGifts(Long id) {
        User user = userService.getCurrentUser();
        SubscribeId subscribeId = new SubscribeId(id, user.getId());
        Subscribe subscribe = getById(subscribeId);
        return subscribe.getWishlist().getGifts();
    }

    public Subscribe getById(SubscribeId id) {
        return repository.findById(id).orElseThrow(SubscriptionNotFoundProblem::new);

    }


}
