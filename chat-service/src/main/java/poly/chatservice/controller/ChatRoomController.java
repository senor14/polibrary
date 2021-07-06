package poly.chatservice.controller;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import poly.chatservice.dto.ChatRoomDto;
import poly.chatservice.jpa.ChatsRoomRepository;
import poly.chatservice.service.impl.ChatRoomService;
import poly.chatservice.util.DateUtil;
import poly.chatservice.vo.ChatRoomResponse;
import poly.chatservice.vo.CreateChatRoomRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;


@Slf4j
@RestController
//@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequiredArgsConstructor
@RequestMapping("/chat-service")
public class ChatRoomController {
//
    private final ChatsRoomRepository chatsRoomRepository;
    private final ChatRoomService chatRoomService;
    private final RestTemplate restTemplate;

    // 모든 채팅방
    @GetMapping("/rooms")
    public ResponseEntity<List<ChatRoomResponse>> room() {

        List<ChatRoomDto> rList = chatsRoomRepository.findAll();

        List<ChatRoomResponse> result = new ArrayList<>();

       rList.forEach(v -> {
           result.add(new ModelMapper().map(v, ChatRoomResponse.class));
       });
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    // 채팅방 생성
    @PostMapping("/rooms")
    public ResponseEntity<ChatRoomResponse> createRoom(@RequestBody CreateChatRoomRequest createChatRoomRequest) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ChatRoomDto chatRoomDto = mapper.map(createChatRoomRequest, ChatRoomDto.class);
        chatRoomDto.setCreatedTime(DateUtil.getDateTime("yyyyMMddhhmmss"));
        chatRoomDto.setRoomId(UUID.randomUUID().toString());

        ChatRoomDto existRooms = chatRoomService.getExistRooms(chatRoomDto);

        if (existRooms != null) {
            List<ChatRoomResponse> result = new ArrayList<>();

            ChatRoomResponse chatRoomResponse = mapper.map(existRooms, ChatRoomResponse.class);

            return ResponseEntity.status(HttpStatus.OK).body(chatRoomResponse);
        }

        chatsRoomRepository.save(chatRoomDto);

        ChatRoomResponse chatRoomResponse = mapper.map(chatRoomDto, ChatRoomResponse.class);
        log.info("chatRoomResponse: {}", chatRoomResponse);
        return ResponseEntity.status(HttpStatus.CREATED).body(chatRoomResponse);
    }

    @GetMapping("/rooms/chat/{roomId}")
    public ResponseEntity<ChatRoomResponse> roomInfo(@PathVariable String roomId) {
        ChatRoomDto chatRoomDto = chatsRoomRepository.findByRoomId(roomId);
        ModelMapper mapper = new ModelMapper();
        ChatRoomResponse chatRoomResponse = mapper.map(chatRoomDto, ChatRoomResponse.class);

        return ResponseEntity.status(HttpStatus.OK).body(chatRoomResponse);
    }

    // 사용자가 요청자인 채팅방 목록 반환
    @ResponseBody
    @GetMapping("/rooms/{userId}/requests")
    public ResponseEntity<List<ChatRoomResponse>> rentalReqRoom(@PathVariable String userId) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<ChatRoomDto> rList = chatRoomService.getReqRooms(userId);

        List<ChatRoomResponse> result = new ArrayList<>();

        rList.forEach(v -> {
            result.add(mapper.map(v, ChatRoomResponse.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

    // 사용자가 배송자인 채팅방 목록 반환
    @ResponseBody
    @GetMapping("/rooms/{userId}/deliveries")
    public ResponseEntity<List<ChatRoomResponse>> deliveryRoom(@PathVariable String userId) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        List<ChatRoomDto> rList = chatRoomService.getDelRooms(userId);

        List<ChatRoomResponse> result = new ArrayList<>();

        rList.forEach(v -> {
            result.add(mapper.map(v, ChatRoomResponse.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);

    }

}