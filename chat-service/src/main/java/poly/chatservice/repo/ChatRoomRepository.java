package poly.chatservice.repo;

import com.mongodb.BasicDBObject;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;
import poly.chatservice.dto.ChatDto;
import poly.chatservice.dto.ChatRoomDto;

import javax.annotation.PostConstruct;
import java.awt.*;
import java.nio.file.attribute.UserPrincipalNotFoundException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
@RequiredArgsConstructor
@Repository
public class ChatRoomRepository {

    private Map<String, ChatRoomDto> chatRoomMap;
    private final MongoTemplate mongodb;

    @PostConstruct
    private void init() {
        chatRoomMap = new LinkedHashMap<>();
    }

//    public List<ChatRoomDto> findAllRoom() {
//        // 채팅방 생성순서 최근 순으로 반환
//        List chatRooms = new ArrayList<>(chatRoomMap.values());
//        Collections.reverse(chatRooms);
//        return chatRooms;
//    }

//    public ChatRoomDto findRoomById(String id) {
//        return chatRoomMap.get(id);
//    }
//
//    public ChatRoomDto createChatRoom(String name) {
//        ChatRoomDto chatRoom = ChatRoomDto.create(name);
//        chatRoomMap.put(chatRoom.getRoomId(), chatRoom);
//        return chatRoom;
//    }


    // 사용자가 배송자인 방 목록 가져오기
    public List<ChatRoomDto> findDelRoom(String userId) {

        List<ChatRoomDto> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection("CHAT_ROOM_INFO");

        Document query = new Document();

        query.append("deliveryId", userId);


        Consumer<Document> processBlock = document -> {
            ChatRoomDto result = new ChatRoomDto();

            result.setRoomId(document.getString("roomId"));
            result.setBookId(document.getLong("bookId"));
            result.setBookName(document.getString("bookName"));
            result.setThumbnail(document.getString("thumbnail"));
            result.setDeliveryId(document.getString("deliveryId"));
            result.setDeliveryName(document.getString("deliveryName"));
            result.setDeliveryDepartment(document.getString("deliveryDepartment"));
            result.setRentalReqId(document.getString("rentalReqId"));
            result.setRentalReqName(document.getString("rentalReqName"));
            result.setRentalReqDepartment(document.getString("rentalReqDepartment"));

            rList.add(result);

            result = null;
        };

        col.find(query).forEach(processBlock);

        return rList;

    }


    // 사용자가 요청자인 방 목록 가져오기
    public List<ChatRoomDto> findReqRoom(String userId) {

        List<ChatRoomDto> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection("CHAT_ROOM_INFO");

        Document query = new Document();

        query.append("rentalReqId", userId);


        Consumer<Document> processBlock = document -> {
            ChatRoomDto result = new ChatRoomDto();

            result.setRoomId(document.getString("roomId"));
            result.setBookId(document.getLong("bookId"));
            result.setBookName(document.getString("bookName"));
            result.setThumbnail(document.getString("thumbnail"));
            result.setDeliveryId(document.getString("deliveryId"));
            result.setDeliveryName(document.getString("deliveryName"));
            result.setDeliveryDepartment(document.getString("deliveryDepartment"));
            result.setRentalReqId(document.getString("rentalReqId"));
            result.setRentalReqName(document.getString("rentalReqName"));
            result.setRentalReqDepartment(document.getString("rentalReqDepartment"));

            rList.add(result);

            result = null;
        };

        col.find(query).forEach(processBlock);

        return rList;

    }


    // 책, 사람1, 사람2 방이 존재하는지 확인
    public ChatRoomDto existRoom(ChatRoomDto chatRoomDto) {
        List<ChatRoomDto> rList = new LinkedList<>();

        MongoCollection<Document> col = mongodb.getCollection("CHAT_ROOM_INFO");

        Document query = new Document();

        query.append("bookId", chatRoomDto.getBookId());
        query.append("deliveryId", chatRoomDto.getDeliveryId());
        query.append("rentalReqId", chatRoomDto.getRentalReqId());

        Consumer<Document> processBlock = document ->  {
            ChatRoomDto result = new ChatRoomDto();

            result.setRoomId(document.getString("roomId"));
            result.setBookId(document.getLong("bookId"));
            result.setBookName(document.getString("bookName"));
            result.setThumbnail(document.getString("thumbnail"));
            result.setDeliveryId(document.getString("deliveryId"));
            result.setDeliveryName(document.getString("deliveryName"));
            result.setDeliveryDepartment(document.getString("deliveryDepartment"));
            result.setRentalReqId(document.getString("rentalReqId"));
            result.setRentalReqName(document.getString("rentalReqName"));
            result.setRentalReqDepartment(document.getString("rentalReqDepartment"));

            rList.add(result);

            result = null;

        };

        col.find(query).forEach(processBlock);

        if (rList == null || rList.size() == 0) {
            return null;
        } else {

            return rList.get(0);
        }
    }
}
