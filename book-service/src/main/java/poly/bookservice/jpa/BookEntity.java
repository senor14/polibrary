package poly.bookservice.jpa;

import lombok.Data;
import javax.persistence.*;
import java.io.Serializable;

import javax.persistence.Entity;

@Entity
@Data
@Table(name = "book_info")
public class BookEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;

    @Column(nullable = false)
    private String isbn;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private String pubInfo;

    @Column(nullable = false)
    private String pubYear;

    @Column(nullable = false)
    private String itemSeq;

    @Column(nullable = false)
    private String thumbnail;

    @Column(nullable = false)
    private String rentalYn;

    @PrePersist
    public void prePersist() {

        this.rentalYn = this.rentalYn == null ? "Y" : this.rentalYn;
    }
}
