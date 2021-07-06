package poly.frontservice.repo;

import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import poly.frontservice.dto.ChatDto;
import poly.frontservice.persistence.comm.MongoDBComon;

import java.time.LocalDateTime;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ChatRepository {

    private final MongoTemplate mongodb;
    private final MongoDBComon mongoDBComon;

    public int insertChat(ChatDto chatDto, String colNm) throws Exception {

        int res = 0;

        if (chatDto == null) {
            chatDto = new ChatDto();
        }
        String[] index = {"roomId"};
        mongoDBComon.createCollection(colNm, index);

        MongoCollection<Document> col = mongodb.getCollection(colNm);

        String chatId = UUID.randomUUID().toString();
        String roomId = chatDto.getRoomId();
        String fromUserId = chatDto.getFromUserId();
        String fromUserName = chatDto.getFromUserName();
        String toUserId = chatDto.getToUserId();
        String toUserName = chatDto.getToUserName();
        String message = chatDto.getMessage();
        String chatTime = chatDto.getChatTime();

        Document doc = new Document();

        doc.append("chatId", chatId);
        doc.append("roomId", roomId);
        doc.append("fromUserId", fromUserId);
        doc.append("fromUserName", fromUserName);
        doc.append("toUserId", toUserId);
        doc.append("toUserName", toUserName);
        doc.append("message", message);
        doc.append("chatTime", chatTime);
        doc.append("chatRead", 0);

        col.insertOne(doc);

        doc = null;
        col = null;

        res = 1;

        return res;
    }
}
