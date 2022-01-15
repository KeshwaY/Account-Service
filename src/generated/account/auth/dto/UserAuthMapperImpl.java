package account.auth.dto;

import account.auth.UserAuth;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2022-01-15T15:07:46+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 17.0.1 (Oracle Corporation)"
)
@Component
public class UserAuthMapperImpl implements UserAuthMapper {

    @Override
    public UserAuth userAuthPostDtoToUserAuth(UserAuthPostDto userAuthPostDto) {
        if ( userAuthPostDto == null ) {
            return null;
        }

        UserAuth userAuth = new UserAuth();

        userAuth.setName( userAuthPostDto.getName() );
        userAuth.setLastname( userAuthPostDto.getLastname() );
        userAuth.setEmail( userAuthPostDto.getEmail() );
        userAuth.setPassword( userAuthPostDto.getPassword() );

        return userAuth;
    }

    @Override
    public UserAuthGetDto userAuthToUserAuthGetDto(UserAuth userAuth) {
        if ( userAuth == null ) {
            return null;
        }

        UserAuthGetDto userAuthGetDto = new UserAuthGetDto();

        userAuthGetDto.setName( userAuth.getName() );
        userAuthGetDto.setLastname( userAuth.getLastname() );
        userAuthGetDto.setEmail( userAuth.getEmail() );

        return userAuthGetDto;
    }

    @Override
    public UserAuthGetDto userAuthPostDtoToUserAuthGetDto(UserAuthPostDto userAuthPostDto) {
        if ( userAuthPostDto == null ) {
            return null;
        }

        UserAuthGetDto userAuthGetDto = new UserAuthGetDto();

        userAuthGetDto.setName( userAuthPostDto.getName() );
        userAuthGetDto.setLastname( userAuthPostDto.getLastname() );
        userAuthGetDto.setEmail( userAuthPostDto.getEmail() );

        return userAuthGetDto;
    }
}
