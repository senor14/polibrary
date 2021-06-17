package poly.frontservice.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;
import poly.frontservice.dto.ChatDto;
import poly.frontservice.dto.ChatRoomDto;

import java.util.List;

public interface ChatsRoomRepository extends MongoRepository<ChatRoomDto, Long> {
    List<ChatRoomDto> findByRoomId(String roomId);

}
