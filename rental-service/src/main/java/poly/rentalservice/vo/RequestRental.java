package poly.rentalservice.vo;

import lombok.Data;

@Data
public class RequestRental {
    private String deliveryUserId;
    private Long bookId;
    private String bookName;
    private String thumbnail;
    private String author;
    private String pubInfo;

}
