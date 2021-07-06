package poly.userservice.vo;

import lombok.Data;

@Data
public class RequestFindInfo {

    private String studentId;
    private String email;
    private String name;
    private String pwd;
    private String nickname;
    private String department;
    private String clientAuth;
    private String auth;
}
