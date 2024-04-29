package gr.project.wishlist.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.OffsetDateTime;

@Entity
@Getter
@Setter
@Table(name = "jn_gift")
public class Gift {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "gift_id_seq")
    @SequenceGenerator(name = "gift_id_seq", sequenceName = "gift_id_seq", allocationSize = 1)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "details")
    private String details;

    @Column(name = "link")
    private String link;

    @Enumerated
    @Column(name = "status", nullable = false)
    private BookingStatus status;

    @ManyToOne
    @JoinColumn(name = "wishlist_id")
    private Wishlist wishlist;

    @ManyToOne
    @JoinColumn(name = "booked_user_id")
    private User bookedPerson;

    @CreationTimestamp
    @JoinColumn(name = "created")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @JoinColumn(name = "updated")
    private OffsetDateTime updatedAt;
}
