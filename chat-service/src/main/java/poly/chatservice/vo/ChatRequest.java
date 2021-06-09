package poly.chatservice.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ChatRequest {

    private Long bookId;
    private String bookName;
    private String toUserId;
    private String fromUserId;
    private String message;
}
