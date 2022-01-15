package account.auth.dto;

import account.auth.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserAuthMapper {

    @Mapping(target = "id", ignore = true)
    User userAuthPostDtoToUser(UserAuthPostDto userAuthPostDto);
    UserAuthGetDto userToUserAuthGetDto(User userAuth);

}
