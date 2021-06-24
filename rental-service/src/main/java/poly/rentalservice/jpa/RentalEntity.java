package poly.rentalservice.jpa;

import lombok.Data;
import org.hibernate.annotations.ColumnDefault;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "rental_info")
public class RentalEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rentalId;

    @Column(nullable = false)
    private String deliveryUserId;

    @Column(nullable = false)
    private String rentalUserId;

    @Column(nullable = false)
    private Long bookId;

    @Column(nullable = false)
    private String bookName;

    @Column(nullable = false)
    private String author;

    @Column
    private String thumbnail;

    @Column(nullable = false)
    private String rentalDate;

    @Column(nullable = false)
    private String pubInfo;


//    @PrePersist
//    public void prePersist() {
//
//
//    }
}
