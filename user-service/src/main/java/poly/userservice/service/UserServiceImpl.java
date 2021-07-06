package poly.userservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import poly.userservice.dto.UserDto;
import poly.userservice.jpa.UserEntity;
import poly.userservice.jpa.UserRepository;
import poly.userservice.vo.ResponseRental;
//import poly.userservice.vo.ResponseRentalRequest;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;
    private final RestTemplate restTemplate;


    @Override
    public UserDto createUser(UserDto userDto) {

        userDto.setUserUuid(UUID.randomUUID().toString());
        userDto.setRegId(userDto.getUserUuid()+"");
        userDto.setChgId(userDto.getUserUuid()+"");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));
        userEntity.setRegDt(LocalDateTime.now());
        userEntity.setChgDt(LocalDateTime.now());

        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }

    @Override
    public UserDto getUserByUserUuid(String userUuid) {
        UserEntity userEntity = userRepository.findByUserUuid(userUuid);

        if (userEntity == null) {
            throw new UsernameNotFoundException("User not found");
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        String rentalUrl = String.format(env.getProperty("rental_service.rentals.url"), userUuid);
        String deliveryUrl = String.format(env.getProperty("rental_service.deliveries.url"), userUuid);

        log.info("rentalUrl: {}", rentalUrl);
        log.info("deliveryUrl: {}", deliveryUrl);


        ResponseEntity<List<ResponseRental>> rentalListResponse =
                restTemplate.exchange(rentalUrl, HttpMethod.GET, null,
                                            new ParameterizedTypeReference<List<ResponseRental>>() {
        });
        log.info("rentalListResponse: {}",rentalListResponse);
        ResponseEntity<List<ResponseRental>> deliveryListResponse =
                restTemplate.exchange(deliveryUrl, HttpMethod.GET, null,
                                            new ParameterizedTypeReference<List<ResponseRental>>() {
                });
        log.info("deliveryListResponse: {}",deliveryListResponse);
        List<ResponseRental> rentalList = rentalListResponse.getBody();
        List<ResponseRental> rentalReqList = deliveryListResponse.getBody();

        userDto.setRentals(rentalList);
        userDto.setDeliveries(rentalReqList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);
        return userDto;
    }

    @Override
    public UserDto editByUserUuid(UserDto userDto) {

        userDto.setChgId(userDto.getUserUuid()+"");

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = mapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        UserDto returnUserDto = mapper.map(userEntity, UserDto.class);

        return returnUserDto;
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(email);

        if (userEntity == null) {
            throw new UsernameNotFoundException(email);
        }

        return new User(userEntity.getEmail(), userEntity.getEncryptedPwd(),
                true, true ,true, true,
                new ArrayList<>()
        );
    }
}
