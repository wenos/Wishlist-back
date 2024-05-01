package gr.project.wishlist.repository;

import gr.project.wishlist.domain.model.Booking;
import gr.project.wishlist.domain.utils.BookingId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, BookingId> {
    List<Booking> findAllByUserId(Long id);
}
