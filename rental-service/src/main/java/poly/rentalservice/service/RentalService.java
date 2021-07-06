package poly.rentalservice.service;

import org.springframework.stereotype.Service;
import poly.rentalservice.dto.RentalDto;
import poly.rentalservice.jpa.RentalEntity;

@Service
public interface RentalService {
    RentalDto createRental(RentalDto rentalDetails);
    RentalDto getRentalByRentalId(Long rentalId);
    Iterable<RentalEntity> getAllRental();
    Iterable<RentalEntity> getRentalsByDeliveryUserId(String userUuid);
    Iterable<RentalEntity> getRentalsByRentalUserId(String userUuid);
//    RentalDto updateRentalStatus(Long rentalId, Integer status);
    RentalDto updateRentalInfo(Long rentalId, RentalDto rentalDto);
    RentalDto deleteRentalInfo(Long rentalId);
}
