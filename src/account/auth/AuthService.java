package account.auth;

import account.auth.dto.UserAuthGetDto;
import account.auth.dto.UserAuthMapper;
import account.auth.dto.UserAuthPostDto;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public final UserAuthMapper mapper;

    public AuthService(UserAuthMapper mapper) {
        this.mapper = mapper;
    }

    public UserAuthGetDto signUser(UserAuthPostDto postDto) {
        return mapper.userAuthPostDtoToUserAuthGetDto(postDto);
    }

}
