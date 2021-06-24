//package poly.rentalservice.jpa;
//
//import lombok.Data;
//import org.hibernate.annotations.ColumnDefault;
//
//import javax.persistence.*;
//import java.io.Serializable;
//import java.time.LocalDateTime;
//
//@Data
//@Entity
//@Table(name = "rental_request_info")
//public class RentalRequestEntity implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long reqId;
//
//    @Column(nullable = false)
//    private String reqUserId;
//
//    @Column(nullable = false)
//    private Long reqBookId;
//
//    @Column(nullable = false)
//    private String reqStatus;
//
//    @Column(nullable = false, updatable = false, insertable = false)
//    @ColumnDefault(value = "CURRENT_TIMESTAMP")
//    private LocalDateTime rentalReqDate;
//
//    @Column(nullable = false)
//    private Integer bbsId;
//
//    @Column(nullable = false)
//    private Long postId;
//
//    @Column(nullable = false)
//    private String regId;
//
//    @Column(nullable = false, updatable = false, insertable = false)
//    @ColumnDefault(value = "CURRENT_TIMESTAMP")
//    private LocalDateTime regDt;
//
//    @Column(nullable = false)
//    private String chgId;
//
//    @Column(nullable = false)
//    private LocalDateTime chgDt;
//
//    @PrePersist
//    public void prePersist() {
//        this.reqStatus = this.reqStatus == null ? "요청중" : this.reqStatus;
//        this.rentalReqDate = this.rentalReqDate == null ? LocalDateTime.now() : this.rentalReqDate;
//        this.regDt = this.regDt == null ? LocalDateTime.now() : this.regDt;
//        this.chgDt = this.chgDt == null ? LocalDateTime.now() : this.chgDt;
//    }
//}
