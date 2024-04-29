package gr.project.wishlist.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Embeddable
@Getter
@Setter
public class BookingId implements Serializable {

    @Column(name = "gift_id")
    private Long giftId;

    @Column(name = "user_id")
    private Long userId;
}
