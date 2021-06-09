package poly.chatservice.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;
import poly.chatservice.dto.ChatDto;

import java.util.List;

public interface ChatsRepository extends MongoRepository<ChatDto, String> {
    List<ChatDto> findByRoomId(String roomId);

}
