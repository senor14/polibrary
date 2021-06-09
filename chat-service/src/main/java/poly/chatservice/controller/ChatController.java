package poly.chatservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.modelmapper.internal.bytebuddy.build.HashCodeAndEqualsPlugin;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import poly.chatservice.dto.ChatDto;
import poly.chatservice.dto.ChatMessage;
import poly.chatservice.jpa.ChatsRepository;
import poly.chatservice.poly.DateUtil;
import poly.chatservice.service.impl.ChatService;
import poly.chatservice.vo.ChatResponse;

import java.util.LinkedList;
import java.util.List;

@Slf4j
@SpringBootApplication
@RequiredArgsConstructor
@Controller
@RequestMapping("/chat-service")
public class ChatController {

    private final SimpMessageSendingOperations messagingTemplate;
    private final ChatService chatService;
    private final ChatsRepository chatsRepository;

    // 채팅리스트
    @GetMapping("/chats/{roomId}")
    public ResponseEntity<List<ChatDto>> roomDetail(@PathVariable String roomId) {
        log.info("roomId: {}", roomId);
        List<ChatDto> rList = chatsRepository.findByRoomId(roomId);
//        List<ChatDto> rList = chatsRepository.findByRoomId(Sorted.by(Sort.Direction.DESC,roomId));
        log.info("rList: {}", rList);
        return ResponseEntity.status(HttpStatus.OK).body(rList);
    }

    @MessageMapping("/chat/message")
//    @MessageMapping("/chat/message")
//    @SendTo("/chatSend/room/{roomId}")
    public void message(@Payload ChatMessage chatMessage) throws Exception{

        log.info("chatMessage: {}", chatMessage.getMessage());

        if (ChatMessage.MessageType.JOIN.equals(chatMessage.getType())){
            chatMessage.setMessage(chatMessage.getFromUserName() + "님이 입장하셨습니다.");
        }else if (ChatMessage.MessageType.TALK.equals(chatMessage.getType())){
            ChatDto chatDto = new ChatDto();

            chatDto.setChatTime(DateUtil.getDateTime("yyyyMMddhhmmss"));

            ModelMapper mapper = new ModelMapper();
            mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

            chatDto = mapper.map(chatMessage, ChatDto.class);
            log.info("chatMessage: {}",chatMessage.toString());

            int res = chatService.insertChat(chatDto);
            log.info("res: {}", res);
        }
        messagingTemplate.convertAndSend("/chat-service/sub/chat/room/"+chatMessage.getRoomId(), chatMessage);
    }

    // 유저의 최근 채팅 2개
    @GetMapping("/chats/{userId}/recentTwo")
    public ResponseEntity<List<ChatResponse>> recentTwoChat(@PathVariable String userId) {
        log.info(this.getClass().getName()+ "recentTwoChat Start!");
        List<ChatDto> chatList = chatService.findRecentTwoChat(userId);

        ModelMapper mapper = new ModelMapper();

        List<ChatResponse> result = new LinkedList<>();
        log.info("result: {}", result);

        chatList.forEach(v -> {
            result.add(mapper.map(v, ChatResponse.class));
        });

        log.info(this.getClass().getName()+ "recentTwoChat End!");
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
