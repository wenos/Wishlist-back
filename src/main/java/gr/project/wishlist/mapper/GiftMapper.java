package gr.project.wishlist.mapper;

import gr.project.wishlist.domain.dto.gift.GiftRequest;
import gr.project.wishlist.domain.dto.gift.GiftResponse;
import gr.project.wishlist.domain.model.Gift;
import java.util.Set;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GiftMapper {

    Gift toEntity(GiftRequest request);

    GiftResponse toResponse(Gift gift);

    List<GiftResponse> toResponse(List<Gift> gifts);

    List<GiftResponse> toResponse(Set<Gift> gifts);

}