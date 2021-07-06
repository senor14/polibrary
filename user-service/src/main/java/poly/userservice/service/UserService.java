package poly.userservice.service;

import org.springframework.security.core.userdetails.UserDetailsService;
import poly.userservice.dto.UserDto;
import poly.userservice.jpa.UserEntity;

public interface UserService extends UserDetailsService {
    UserDto createUser(UserDto userDto);

    UserDto getUserByUserUuid(String userUuid);

    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);

    UserDto editByUserUuid(UserDto userDto);

}
