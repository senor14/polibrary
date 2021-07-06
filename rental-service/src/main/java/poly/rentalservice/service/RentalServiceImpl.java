package poly.rentalservice.service;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import poly.rentalservice.dto.RentalDto;
import poly.rentalservice.jpa.RentalEntity;
import poly.rentalservice.jpa.RentalRepository;

@Data
@Slf4j
@Service
@RequiredArgsConstructor
public class RentalServiceImpl implements RentalService{

    private final RentalRepository rentalRepository;

    @Transactional
    @Override
    public RentalDto createRental(RentalDto rentalDto) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RentalEntity rentalEntity = mapper.map(rentalDto, RentalEntity.class);

        rentalRepository.save(rentalEntity);

        RentalDto returnValue = mapper.map(rentalEntity, RentalDto.class);

        return returnValue;
    }

    @Transactional
    @Override
    public RentalDto getRentalByRentalId(Long rentalId) {
        RentalEntity rentalEntity = rentalRepository.findByRentalId(rentalId);
        RentalDto rentalDto = new ModelMapper().map(rentalEntity, RentalDto.class);

        return rentalDto;
    }

    @Transactional
    @Override
    public Iterable<RentalEntity> getAllRental() {
        return rentalRepository.findAll();
    }

    @Transactional
    @Override
    public Iterable<RentalEntity> getRentalsByDeliveryUserId(String userId) {
        return rentalRepository.findByDeliveryUserId(userId);
    }

    @Transactional
    @Override
    public Iterable<RentalEntity> getRentalsByRentalUserId(String userId) {
        return rentalRepository.findByRentalUserId(userId);
    }

    @Transactional
    @Override
    public RentalDto updateRentalInfo(Long rentalId, RentalDto rentalDto) {
        RentalEntity rentalEntity = rentalRepository.findByRentalId(rentalId);
        log.info("rentalEntity: {}", rentalEntity );
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        log.info("rentalDto: {}", rentalDto );
        mapper.map(rentalDto, rentalEntity);

        rentalRepository.save(rentalEntity);

        mapper.map(rentalEntity, rentalDto);

        return rentalDto;
    }

    @Transactional
    @Override
    public RentalDto deleteRentalInfo(Long rentalId) {

        RentalEntity rentalEntity = rentalRepository.findByRentalId(rentalId);

        ModelMapper mapper = new ModelMapper();

        RentalDto rentalDto = mapper.map(rentalEntity, RentalDto.class);

        rentalRepository.delete(rentalEntity);

        return rentalDto;
    }

//    @Override
//    public RentalDto updateRentalStatus(Long rentalId, Integer status) {
//
//        ModelMapper mapper = new ModelMapper();
//
//        RentalEntity rentalEntity = rentalRepository.findByRentalId(rentalId);
//        rentalEntity.setStatus(status);
//        rentalRepository.save(rentalEntity);
//
//        RentalDto rentalDto = mapper.map(rentalEntity, RentalDto.class);
//
//        return rentalDto;
//    }


}
