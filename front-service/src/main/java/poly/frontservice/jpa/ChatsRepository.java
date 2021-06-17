package poly.frontservice.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;
import poly.frontservice.dto.ChatDto;

import java.util.List;

public interface ChatsRepository extends MongoRepository<ChatDto, String> {
    List<ChatDto> findByRoomId(String roomId);

}
