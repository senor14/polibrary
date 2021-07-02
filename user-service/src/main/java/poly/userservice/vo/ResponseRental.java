package poly.userservice.vo;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ResponseRental {

    private Long rentalId;

    private String deliveryUserId;
    private String rentalUserId;
    private String rentalDate;
    private Long bookId;
    private String bookName;
    private String author;
    private String pubInfo;
    private String thumbnail;
    private String status;
}
