package gr.project.wishlist.repository;


import gr.project.wishlist.domain.model.Subscribe;
import gr.project.wishlist.domain.utils.SubscribeId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubscribeRepository extends JpaRepository<Subscribe, SubscribeId> {
    List<Subscribe> findAllByUserId(Long id);
}
