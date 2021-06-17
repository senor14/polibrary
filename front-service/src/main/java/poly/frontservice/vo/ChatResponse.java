package poly.frontservice.vo;

import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;

@Data
public class ChatResponse {

    private String chatId;
    private Long bookId;
    private String bookName;
    private String fromUserId;
    private String fromUserName;
    private String toUserId;
    private String toUserName;
    private String message;
    private String chatTime;
    private Integer chatRead;

//    public ChatResponse(ChatInfo document) {
//        this.chatId = document.getChatId();
//        this.bookId = document.getBookId();
//        this.toUserId = document.getToUserId();
//        this.fromUserId = document.getFromUserId();
//        this.message = document.getMessage();
//        this.chatTime = document.getChatTime();
//        this.chatRead = document.getChatRead();
//    }
}
