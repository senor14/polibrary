package poly.frontservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
public class ChatMessage {
    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK , JOIN
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String sender; // 메시지 보낸사람
    private String message; // 메시지

//    private String roomId; // 방 번호
//    private String fromUserId; // 메시지 보낸사람 번호
//    private String fromUserName; // 메시지 보낸사람 이름
//    private String toUserId; // 메시지 받는사람 번호
//    private String toUserName; // 메시지 받는사람 이름
//    private String message; // 메시지
//
//    @Override
//    public String toString() {
//        return "ChatMessage{" +
//                "roomId='" + roomId + '\'' +
//                ", fromUserId='" + fromUserId + '\'' +
//                ", fromUserName='" + fromUserName + '\'' +
//                ", toUserId='" + toUserId + '\'' +
//                ", toUserName='" + toUserName + '\'' +
//                ", message='" + message + '\'' +
//                '}';
//    }
}