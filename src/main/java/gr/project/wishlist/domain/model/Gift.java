package gr.project.wishlist.domain.model;

import gr.project.wishlist.domain.utils.BookingStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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

    @OneToOne(mappedBy = "gift", orphanRemoval = true)
    private Booking booking;

    @CreationTimestamp
    @Column(name = "created")
    private OffsetDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated")
    private OffsetDateTime updatedAt;
}
