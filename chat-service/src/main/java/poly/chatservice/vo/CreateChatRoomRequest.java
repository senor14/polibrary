package poly.chatservice.vo;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestParam;

@Data
public class CreateChatRoomRequest {

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
