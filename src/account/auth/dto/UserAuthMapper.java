package account.auth.dto;

import account.auth.UserAuth;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserAuthMapper {

    UserAuth userAuthPostDtoToUserAuth(UserAuthPostDto userAuthPostDto);
    UserAuthGetDto userAuthToUserAuthGetDto(UserAuth userAuth);

    UserAuthGetDto userAuthPostDtoToUserAuthGetDto(UserAuthPostDto userAuthPostDto);

}
