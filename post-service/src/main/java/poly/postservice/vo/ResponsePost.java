package poly.postservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;


@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponsePost {
    private Long postId;
    private String userUuid;
    private String nickname;
    private String department;
    private Long bookId;
    private String title;
    private String author;
    private String thumbnail;
    private Integer status;
    private String dispYn;
    private String regDt;
}
