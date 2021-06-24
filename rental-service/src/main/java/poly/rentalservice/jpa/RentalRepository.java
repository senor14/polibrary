package poly.rentalservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface RentalRepository extends CrudRepository<RentalEntity, Long>, JpaRepository<RentalEntity, Long> {
    RentalEntity findByRentalId(Long rentalId);
    Iterable<RentalEntity> findByRentalUserId(String rentalUserId);
    Iterable<RentalEntity> findByDeliveryUserId(String deliveryUserId);

    @Query (
            value = "SELECT COUNT (r) FROM RentalEntity r WHERE r.rentalUserId = :userId"
    )
    Integer countMyRentals(String userId);
}