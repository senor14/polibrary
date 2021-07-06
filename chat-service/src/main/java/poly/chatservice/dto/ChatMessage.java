package poly.chatservice.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ChatMessage {

    // 메시지 타입 : 입장, 채팅
    public enum MessageType {
        ENTER, TALK , JOIN
    }

    private MessageType type; // 메시지 타입
    private String roomId; // 방번호
    private String fromUserName; // 메시지 보낸사람
    private String message; // 메시지

    @Override
    public String toString() {
        return "ChatMessage{" +
                "type=" + type +
                ", roomId='" + roomId + '\'' +
                ", fromUserName='" + fromUserName + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}