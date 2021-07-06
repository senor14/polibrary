//package poly.chatservice.persistence.mongo.impl;
//
//import com.mongodb.client.MongoCollection;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.bson.Document;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.mapping.MongoId;
//import org.springframework.stereotype.Component;
//import poly.chatservice.dto.ChatDto;
//import poly.chatservice.persistence.comm.AbstractMongoDBComon;
//import poly.chatservice.persistence.mongo.IChatMapperUsingDto;
//import poly.chatservice.poly.CmmUtil;
//
//import java.util.logging.Logger;
//
//@Component("ChatMapperUsingDto")
//public class ChatMapperUsingDto extends AbstractMongoDBComon implements IChatMapperUsingDto {
//
//    @Autowired
//    private MongoTemplate mongodb;
//
//    public ChatMapperUsingDto(MongoTemplate mongodb) {
//        super(mongodb);
//    }
//
//    @Override
//    public int insertChat(ChatDto chatDto, String colNm) throws Exception {
//
//        int res = 0;
//
//
//        if (chatDto == null) {
//            chatDto = new ChatDto();
//        }
//        super.createCollection(colNm, "collectTime");
//
//        MongoCollection<Document> col = mongodb.getCollection(colNm);
//
//        Long fromUserId = chatDto.getFromUserId();
//        String fromUserName = chatDto.getFromUserName();
//        Long toUserId = chatDto.getToUserId();
//        String toUserName = chatDto.getToUserName();
//        String message = chatDto.getMessage();
//        String chatTime = chatDto.getChatTime();
//
//        Document doc = new Document();
//
//        doc.append("fromUserId", fromUserId);
//        doc.append("fromUserName", fromUserName);
//        doc.append("toUserId", toUserId);
//        doc.append("toUserName", toUserName);
//        doc.append("message", message);
//        doc.append("chatTime", chatTime);
//        doc.append("chatRead", 0);
//
//        col.insertOne(doc);
//
//        doc = null;
//        col = null;
//
//        res = 1;
//
//        return res;
//    }
//
//
//}
