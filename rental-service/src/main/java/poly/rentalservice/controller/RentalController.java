package poly.rentalservice.controller;

import io.micrometer.core.annotation.Timed;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import poly.rentalservice.dto.RentalDto;
import poly.rentalservice.jpa.RentalEntity;
import poly.rentalservice.jpa.RentalRepository;
import poly.rentalservice.service.RentalService;
import poly.rentalservice.util.DateUtil;
import poly.rentalservice.vo.RequestRental;
import poly.rentalservice.vo.ResponseRental;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/rental-service")
public class RentalController {
    private final Environment env;
    private final RentalService rentalService;
    private final RentalRepository rentalRepository;


    @GetMapping("/hello")
    public String Hello() {
        return "Hello";
    }

    @GetMapping("/health_check")
    @Timed(value="users.status", longTask = true)
    public String status() {
        return String.format("It's Working in User Service"
                + ", port(local.server.port)=" + env.getProperty("local.server.port")
                + ", port(server.port)=" + env.getProperty("server.port")
                + ", token secret=" + env.getProperty("token.secret")
                + ", token expiration time=" + env.getProperty("token.expiration_time"));
    }


    // 전체 대여 리스트
    @GetMapping("/rentals")
    public ResponseEntity<List<ResponseRental>> getRentals() {
        Iterable<RentalEntity> userList = rentalService.getAllRental();

        List<ResponseRental> result = new ArrayList<>();

        userList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseRental.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }


//    @GetMapping("/rental_requests")
//    public ResponseEntity<List<ResponseRentalRequest>> getRentalRequests() {
//        Iterable<RentalRequestEntity> userList = rentalRequestService.getAllRentalRequest();
//
//        List<ResponseRentalRequest> result = new ArrayList<>();
//
//        userList.forEach(v -> {
//            result.add(new ModelMapper().map(v, ResponseRentalRequest.class));
//        });
//
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }

    // 대여 생성
    @PostMapping("/{userId}/rentals")
    public ResponseEntity<ResponseRental> createRental(@PathVariable("userId") String userUuid,
                                                       @RequestBody RequestRental rentalDetails) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RentalDto rentalDto = mapper.map(rentalDetails, RentalDto.class);
        rentalDto.setRentalDate(DateUtil.getDateTime("yyyyMMddhhmmss"));
        rentalDto.setRentalUserId(userUuid);
        RentalDto createdRequest = rentalService.createRental(rentalDto);

        ResponseRental responseRental = mapper.map(createdRequest, ResponseRental.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseRental);
    }

//    @PostMapping("/{userId}/rental_requests")
//    public ResponseEntity<ResponseRentalRequest> createRentalRequest(@PathVariable("userId") String userUuid,
//                                                                   @RequestBody RequestRentalRequest rentalRequestDetails) {
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
//        RentalRequestDto rentalRequestDto = mapper.map(rentalRequestDetails, RentalRequestDto.class);
//        rentalRequestDto.setReqUserId(userUuid);
//        RentalRequestDto createdRentalRequest = rentalRequestService.createRentalRequest(rentalRequestDto);
//
//        ResponseRentalRequest responseRentalRequest = mapper.map(createdRentalRequest, ResponseRentalRequest.class);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(responseRentalRequest);
//    }

    // 내 대여 정보
    @GetMapping("/{userId}/rentals")
    public ResponseEntity<List<ResponseRental>> getMyRental(@PathVariable("userId") String userUuid) {
        Iterable<RentalEntity> rentalList = rentalService.getRentalsByRentalUserId(userUuid);

        List<ResponseRental> result = new ArrayList();
        rentalList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseRental.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 내 배송 정보
    @GetMapping("/{userId}/deliveries")
    public ResponseEntity<List<ResponseRental>> getMyDelivery(@PathVariable("userId") String userUuid) {
        Iterable<RentalEntity> rentalList = rentalService.getRentalsByDeliveryUserId(userUuid);

        List<ResponseRental> result = new ArrayList();
        rentalList.forEach(v -> {
            result.add(new ModelMapper().map(v, ResponseRental.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
//    @GetMapping("/{userId}/rental_requests")
//    public ResponseEntity<List<ResponseRentalRequest>> getRentalRequest(@PathVariable("userId") String userId) {
//        return getListResponseEntity(userId);
//    }
//
//    @GetMapping("/{userId}/rental_deliveries")
//    public ResponseEntity<List<ResponseRentalRequest>> getRentalDelivery(@PathVariable("userId") String userId) {
//        return getListResponseEntity(userId);
//    }

//    private ResponseEntity<List<ResponseRentalRequest>> getListResponseEntity(@PathVariable("userId") String userId) {
//        Iterable<RentalRequestEntity> rentalRequestList = rentalRequestService.getRentalRequestsByUserId(userId);
//
//        List<ResponseRentalRequest> result = new ArrayList();
//        rentalRequestList.forEach(v -> {
//            result.add(new ModelMapper().map(v, ResponseRentalRequest.class));
//        });
//
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//    }

    // 대여 정보 수정
    @PutMapping("/rentals/{rentalId}")
    public ResponseEntity<ResponseRental> updateRentalInfo(@PathVariable Long rentalId,
                                                           @RequestBody RentalDto rentalDto) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RentalDto resultDto = rentalService.updateRentalInfo(rentalId, rentalDto);

        ResponseRental result = mapper.map(rentalDto, ResponseRental.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    // 대여정보 삭제
    @DeleteMapping("/rentals/{rentalId}")
    public ResponseEntity<ResponseRental> deleteRentalInfo(@PathVariable Long rentalId) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        RentalDto rentalDto = rentalService.deleteRentalInfo(rentalId);

        ResponseRental result = mapper.map(rentalDto, ResponseRental.class);

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }



    // 나의 총 대여권 수
    @GetMapping("/{userId}/countMyRentals")
    public ResponseEntity<Map<String, Integer>> countMyRentals(@PathVariable String userId) {
        Integer rentalCount = rentalRepository.countMyRentals(userId);

        Map myRental = new HashMap();
        myRental.put("rentalCount", rentalCount);
        return ResponseEntity.status(HttpStatus.OK).body(myRental);

    }
//
//    @GetMapping("/{rentalId}/status/{status}")
//    public ResponseEntity<ResponseRental> changeRentalStatus(@PathVariable Long rentalId,
//                                                        @PathVariable Integer status) {
//
//        ModelMapper mapper = new ModelMapper();
//
//        RentalDto rentalDto = rentalService.updateRentalStatus(rentalId, status);
//
//        ResponseRental result = mapper.map(rentalDto, ResponseRental.class);
//
//        return ResponseEntity.status(HttpStatus.OK).body(result);
//
//    }


}
