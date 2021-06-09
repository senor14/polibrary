package poly.chatservice.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;
import poly.chatservice.dto.ChatDto;
import poly.chatservice.dto.ChatRoomDto;

import java.util.List;

public interface ChatsRoomRepository extends MongoRepository<ChatRoomDto, Long> {
    ChatRoomDto findByRoomId(String roomId);

}
