package gr.project.wishlist.domain.dto.link;

import gr.project.wishlist.domain.model.AccessMode;

import java.util.UUID;

public record LinkResponse(
        UUID id,
        AccessMode accessMode
) {
}
