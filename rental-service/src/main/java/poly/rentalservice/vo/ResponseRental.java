package poly.rentalservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseRental implements Serializable {
    private Long rentalId;

    private String deliveryUserId;
    private String rentalUserId;
    private String rentalDate;
    private Long bookId;
    private String bookName;
    private String author;
    private String pubInfo;
    private String thumbnail;

}
