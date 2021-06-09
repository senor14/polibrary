package poly.chatservice.repo;

import com.mongodb.client.MongoCollection;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import poly.chatservice.dto.ChatDto;
import poly.chatservice.persistence.comm.MongoDBComon;
import poly.chatservice.poly.CmmUtil;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.function.Consumer;

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

    public List<ChatDto> findRecentTwoChat(String userId) {

        List<ChatDto> resultList = new ArrayList<>();


        MongoCollection<Document> collection = mongodb.getCollection("CHAT_INFO");

        // Created with Studio 3T, the IDE for MongoDB - https://studio3t.com/

        Document query = new Document();

        query.append("toUserId", userId);
        log.info("query: {}", query);
        Document sort = new Document();

        sort.append("chatTime", -1);
        log.info("sord: {}",sort);
        int limit = 2;

        Consumer<Document> processBlock = document -> {
            ChatDto result = new ChatDto();

            result.setChatId(CmmUtil.nvl(document.getString("chatId")));
            result.setRoomId(CmmUtil.nvl(document.getString("roomId")));
            result.setFromUserId(CmmUtil.nvl(document.getString("fromUserId")));
            result.setFromUserName(CmmUtil.nvl(document.getString("fromUserName")));
            result.setToUserId(CmmUtil.nvl(document.getString("toUserId")));
            result.setToUserName(CmmUtil.nvl(document.getString("toUserName")));
            result.setMessage(CmmUtil.nvl(document.getString("message")));
            result.setChatTime(CmmUtil.nvl(document.getString("chatTime")));
            result.setChatRead(document.getInteger("chatRead"));

            resultList.add(result);
            log.info("result: {}",result);
            result = null;
        };

        collection.find(query).sort(sort).limit(limit).forEach(processBlock);

        return resultList;
    }

}
