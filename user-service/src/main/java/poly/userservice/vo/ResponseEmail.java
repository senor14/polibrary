package poly.userservice.vo;

import lombok.Data;

@Data
public class ResponseEmail {

    private String toMail;
    private String title;
    private String contents;
    private String auth;
    private String msg;
    private String url;

}
