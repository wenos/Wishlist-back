package gr.project.wishlist.mapper;

import gr.project.wishlist.domain.dto.giftlist.WishlistRequest;
import gr.project.wishlist.domain.dto.giftlist.WishlistResponse;
import gr.project.wishlist.domain.model.Wishlist;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface WishlistMapper {

    Wishlist toEntity(WishlistRequest request);

    WishlistResponse toResponse(Wishlist wishlist);

    List<WishlistResponse> toResponse(List<Wishlist> wishlists);

}