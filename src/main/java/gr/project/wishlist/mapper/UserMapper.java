package gr.project.wishlist.mapper;

import gr.project.wishlist.domain.dto.user.UserResponse;
import gr.project.wishlist.domain.model.User;
import org.mapstruct.Mapper;

import java.util.List;



@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse toResponse(User user);

    List<UserResponse> toResponse(List<User> users);

}