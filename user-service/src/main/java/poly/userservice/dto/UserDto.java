package poly.userservice.dto;

import lombok.Data;
import poly.userservice.vo.ResponseRental;
//import poly.userservice.vo.ResponseRentalRequest;

import java.util.List;

@Data
public class UserDto {
    private Long userId;
    private String userUuid;
    private String email;
    private String name;
    private String studentId;
    private String pwd;
    private String department;
    private String nickname;
    private Integer point;
    private Integer authLv;
    private String useYn;
    private String regId;
    private String chgId;

    private String encryptedPwd;

    private List<ResponseRental> deliveries;
    private List<ResponseRental> rentals;
}
