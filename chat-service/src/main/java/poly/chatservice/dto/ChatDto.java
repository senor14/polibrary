package poly.chatservice.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Objects;

@Data
@Document(collection = "CHAT_INFO")
public class ChatDto {

    @Id
    private String chatId;

    private String roomId;
    private String fromUserId;
    private String fromUserName;
    private String toUserId;
    private String toUserName;
    private String message;
    private String chatTime;
    private Integer chatRead;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChatDto chatDto = (ChatDto) o;
        return Objects.equals(chatId, chatDto.chatId) && Objects.equals(roomId, chatDto.roomId) && Objects.equals(fromUserId, chatDto.fromUserId) && Objects.equals(fromUserName, chatDto.fromUserName) && Objects.equals(toUserId, chatDto.toUserId) && Objects.equals(toUserName, chatDto.toUserName) && Objects.equals(message, chatDto.message) && Objects.equals(chatTime, chatDto.chatTime) && Objects.equals(chatRead, chatDto.chatRead);
    }

    @Override
    public int hashCode() {
        return Objects.hash(chatId, roomId, fromUserId, fromUserName, toUserId, toUserName, message, chatTime, chatRead);
    }
}
