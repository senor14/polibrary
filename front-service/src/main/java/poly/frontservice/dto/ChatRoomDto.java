package poly.frontservice.dto;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@Document(collection = "CHAT_ROOM_INFO")
public class ChatRoomDto {

    @Id
    private String Id;

    private String roomId;
    private Long bookId;
    private String bookName;
    private String deliveryId;
    private String deliveryName;
    private String deliveryDepartment;
    private String rentalReqId;
    private String rentalReqName;
    private String rentalReqDepartment;
    private String createdTime;
    private String thumbnail;

//   public static ChatRoomDto create(Long bookId, String bookName,
//                                    Long deliveryId, String deliveryName, String deliveryDepartment,
//                                    Long rentalReqId, String rentalReqName, String rentalReqDepartment) {
//
//       ChatRoomDto chatRoomDto = new ChatRoomDto();
//       chatRoomDto.bookId = bookId;
//       chatRoomDto.bookName =bookName;
//       chatRoomDto.deliveryId =deliveryId;
//       chatRoomDto.deliveryName =deliveryName;
//       chatRoomDto.deliveryDepartment =deliveryDepartment;
//       chatRoomDto.rentalReqId =rentalReqId;
//       chatRoomDto.rentalReqName =rentalReqName;
//       chatRoomDto.rentalReqDepartment =rentalReqDepartment;
//
//       return chatRoomDto;
//   }



}