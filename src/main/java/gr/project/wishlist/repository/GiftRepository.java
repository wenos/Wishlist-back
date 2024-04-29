package gr.project.wishlist.repository;

import gr.project.wishlist.domain.model.Gift;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GiftRepository extends JpaRepository<Gift, Long> {

    List<Gift> findGiftsByWishlistId(Long wishlistId);

}
