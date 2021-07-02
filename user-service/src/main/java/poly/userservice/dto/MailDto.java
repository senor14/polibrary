package poly.userservice.dto;

import lombok.Data;

@Data
public class MailDto {

    private String toMail;
    private String title;
    private String contents;
}
