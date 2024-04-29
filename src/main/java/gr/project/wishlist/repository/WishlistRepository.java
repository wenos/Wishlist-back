package gr.project.wishlist.repository;

import gr.project.wishlist.domain.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {
    Boolean existsByTitle(String title);

    List<Wishlist> findAllByOwnerId(Long ownerId);

    void deleteAllByOwnerId(Long ownerId);
}
