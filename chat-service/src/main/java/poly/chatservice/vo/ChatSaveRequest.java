package poly.chatservice.vo;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ChatSaveRequest {

    private Long bookId;
    private String bookName;
    private String fromUserId;
    private String fromUserName;
    private String toUserId;
    private String toUserName;
    private String message;
    private LocalDateTime chatTime = LocalDateTime.now();
    private Integer chatRead = 0;

    @Builder
    public ChatSaveRequest(Long bookId, String bookName, String fromUserId, String fromUserName,
                           String toUserId, String toUserName, String message, LocalDateTime chatTime,
                           Integer chatRead) {
        this.bookId = bookId;
        this.bookName = bookName;
        this.fromUserId = fromUserId;
        this.fromUserName = fromUserName;
        this.toUserId = toUserId;
        this.toUserName = toUserName;
        this.message = message;
        this.chatTime = chatTime;
        this.chatRead = chatRead;
    }



}
