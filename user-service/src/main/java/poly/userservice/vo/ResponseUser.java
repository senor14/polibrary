package poly.userservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String userUuid;
    private String email;
    private String name;
    private String nickname;
    private String department;
    private String studentId;
    private String point;

    private String msg;
    private String url;

//    private List<ResponseRentalRequest> rentalReqs;
    private List<ResponseRental> rentals;
}
