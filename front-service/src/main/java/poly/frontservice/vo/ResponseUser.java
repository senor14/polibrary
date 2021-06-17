package poly.frontservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String studentId;
    private String department;
    private String nickname;
    private String userUuid;

    private String msg;
    private String url;

    private List<ResponseRental> deliveries;
    private List<ResponseRental> rentals;
}
