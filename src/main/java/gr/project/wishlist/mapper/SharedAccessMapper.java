package gr.project.wishlist.mapper;

import gr.project.wishlist.domain.dto.link.LinkResponse;
import gr.project.wishlist.domain.model.SharedAccess;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface SharedAccessMapper {

    LinkResponse toResponse(SharedAccess sharedAccess);
}
