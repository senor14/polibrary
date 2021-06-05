package poly.bookservice.jpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface BookRepository extends CrudRepository<BookEntity, Long>, JpaRepository<BookEntity, Long> {

    BookEntity findByBookId(Long bookId);

    @Query(
            value = " SELECT b FROM BookEntity b WHERE b.title LIKE %:keyword% OR b.author LIKE %:keyword% ORDER BY b.bookId DESC"
//            countQuery = "SELECT COUNT(b.bookId) FROM BookEntity b WHERE b.title LIKE %:title% OR b.author LIKE %:author%"
    )
    List<BookEntity> findAllSearch(String keyword);
    
    @Query(
            value = "SELECT b FROM BookEntity b ORDER BY b.bookId DESC LIMIT 100", nativeQuery = true
    )
    List<BookEntity> findNewBook100();

    List<BookEntity> findTop100ByOrderByBookIdDesc();
}
