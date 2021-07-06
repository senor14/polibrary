package poly.chatservice.vo;

import lombok.Data;
import poly.chatservice.dto.ChatDto;

@Data
public class ChatRoomResponse {

    private String roomId;

    private Long bookId;
    private String bookName;
    private String thumbnail;
    private String deliveryId;
    private String deliveryName;
    private String deliveryDepartment;
    private String rentalReqId;
    private String rentalReqName;
    private String rentalReqDepartment;


}
