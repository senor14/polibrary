package poly.rentalservice.dto;

import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
public class RentalDto implements Serializable {
    private Long rentalId;
    private String rentalUserId;
    private String deliveryUserId;
    private Long bookId;
    private String bookName;
    private String rentalDate;
    private String thumbnail;
    private String author;
    private String pubInfo;

}
