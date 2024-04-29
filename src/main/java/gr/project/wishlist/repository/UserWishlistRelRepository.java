package gr.project.wishlist.repository;


import gr.project.wishlist.domain.model.Subscribe;
import gr.project.wishlist.domain.model.UserWishlistRelId;
import gr.project.wishlist.domain.model.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserWishlistRelRepository extends JpaRepository<Subscribe, UserWishlistRelId> {

    boolean existsById(UserWishlistRelId id);

    List<Subscribe> findAllByUserId(Long id);

}
