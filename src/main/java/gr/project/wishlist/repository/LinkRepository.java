package gr.project.wishlist.repository;

import com.fasterxml.jackson.annotation.JsonProperty;
import gr.project.wishlist.domain.model.AccessMode;
import gr.project.wishlist.domain.model.SharedAccess;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface LinkRepository extends JpaRepository<SharedAccess, UUID> {

    Optional<SharedAccess> findByWishlistIdAndAccessMode(Long wishlistId, AccessMode accessMode);
}
