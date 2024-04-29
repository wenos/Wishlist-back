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
public class SubscribeId implements Serializable {

    @Column(name = "wishlist_id")
    private Long wishlistId;

    @Column(name = "user_id")
    private Long userId;
}
