//package poly.rentalservice.service;
//
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.modelmapper.ModelMapper;
//import org.modelmapper.convention.MatchingStrategies;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import poly.rentalservice.dto.RentalRequestDto;
//import poly.rentalservice.jpa.RentalRequestEntity;
//import poly.rentalservice.jpa.RentalRequestRepository;
//
//@Data
//@Slf4j
//@Service
//@RequiredArgsConstructor
//public class RentalRequestServiceImpl implements RentalRequestService{
//
//    private final RentalRequestRepository rentalRequestRepository;
//
//    @Transactional
//    @Override
//    public RentalRequestDto createRentalRequest(RentalRequestDto rentalRequestDto) {
//
//        /**
//         * reqId, reqUserId, reqbookId 값 추가할 것
//         * bookName, author, pub_info 값 추가할 것
//         * # postId 에서 값 추출하면 될 듯
//         **/
//        rentalRequestDto.setRegId(rentalRequestDto.getReqUserId()+"");
//        rentalRequestDto.setChgId(rentalRequestDto.getReqUserId()+"");
//
//        ModelMapper mapper = new ModelMapper();
//        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//        RentalRequestEntity rentalRequestEntity = mapper.map(rentalRequestDto, RentalRequestEntity.class);
//
//        rentalRequestRepository.save(rentalRequestEntity);
//
//        RentalRequestDto returnValue = mapper.map(rentalRequestEntity, RentalRequestDto.class);
//
//        return returnValue;
//    }
//
//    @Transactional
//    @Override
//    public RentalRequestDto getRentalByRentalRequestId(Long reqId) {
//        RentalRequestEntity rentalRequestEntity = rentalRequestRepository.findByReqId(reqId);
//        RentalRequestDto rentalRequestDto = new ModelMapper().map(rentalRequestEntity, RentalRequestDto.class);
//
//        return rentalRequestDto;
//    }
//
//    @Transactional
//    @Override
//    public Iterable<RentalRequestEntity> getAllRentalRequest() {
//        return rentalRequestRepository.findAll();
//    }
//
//    @Transactional
//    @Override
//    public Iterable<RentalRequestEntity> getRentalRequestsByUserId(String userId) {
//        return rentalRequestRepository.findByReqUserId(userId);
//    }
//}
