package poly.postservice.vo;

import lombok.Getter;

@Getter
public class RequestCreatePost {

    private String userUuid;
    private String nickname;
    private String department;
    private Long bookId;
    private String title;
    private String author;
    private String thumbnail;
}
