package poly.userservice.vo;

import lombok.Data;

@Data
public class ResponseModel {

    private String msg;
    private String url;

    private String userUuid;
    private String email;
    private String name;
    private String nickname;
    private String department;
    private String studentId;
    private Integer point;
}
