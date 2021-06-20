package poly.postservice.persistence.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PostRepository extends CrudRepository<PostEntity, Long>, JpaRepository<PostEntity, Long> {
    PostEntity findByPostId(Long postId);

    @Transactional
    int deleteByPostId(Long postId);

    List<PostEntity> findAllByUserUuidOrderByRegDtDesc(String userUuid);

    @Query(
            value = " SELECT p FROM PostEntity p WHERE p.userUuid = :userUuid AND p.status = 2"
    )
    List<PostEntity> findSuccessPosts(String userUuid);

    @Query(
            value = "SELECT p.postId FROM PostEntity p WHERE p.userUuid = :userUuid AND p.bookId = :bookId"
    )
    Long userBookPostIds(String userUuid, Long bookId);
}
