package poly.userservice.jpa;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, String> {
    UserEntity findByUserUuid(String userUuid);

    UserEntity findByEmail(String email);

    UserEntity findByStudentId(String studentId);

    boolean existsByUserUuid(String userUuid);

    boolean existsByStudentId(String existsByStudentId);
    boolean existsByNickname(String existsByNickname);
    boolean existsByEmail(String existsByEmail);

    @Transactional
    int deleteByUserUuid(String userUuid);
}
