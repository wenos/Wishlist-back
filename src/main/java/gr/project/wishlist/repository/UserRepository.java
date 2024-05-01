package gr.project.wishlist.repository;

import gr.project.wishlist.domain.utils.Role;
import gr.project.wishlist.domain.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.OffsetDateTime;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);


    @Query("SELECT u FROM User u WHERE " +
            "(:username IS null OR u.username LIKE %:username%) " +
            "AND (:email IS null OR u.email LIKE %:email%) " +
            "AND (:role IS null OR u.role = :role) " +
            "AND (:isBanned IS null OR :isBanned = true AND u.banned IS NOT NULL AND u.banned > :time " +
            "OR (u.banned IS NULL OR u.banned <= :time) AND :isBanned = false) " +
            "AND (:id IS null OR u.id = :id)")
    Page<User> findAllWithFilter(
            @Param("id") Long id,
            @Param("username") String username,
            @Param("email") String email,
            @Param("role") Role role,
            @Param("isBanned") Boolean isBanned,
            @Param("time") OffsetDateTime time,
            Pageable pageable);
}
