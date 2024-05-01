package gr.project.wishlist.domain.dto.link;

import gr.project.wishlist.domain.utils.AccessMode;

import java.util.UUID;

public record LinkResponse(
        UUID id,
        AccessMode accessMode
) {
}
