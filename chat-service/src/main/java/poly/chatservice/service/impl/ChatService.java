package poly.chatservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poly.chatservice.dto.ChatDto;
import poly.chatservice.jpa.ChatsRoomRepository;
import poly.chatservice.poly.DateUtil;
import poly.chatservice.repo.ChatRepository;
import poly.chatservice.repo.ChatRoomRepository;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatService {

    private final ChatRepository chatRepository;

    public int insertChat(ChatDto chatDto) throws Exception {

        int res = 0;

        String colNm = "CHAT_INFO";

        res = chatRepository.insertChat(chatDto, colNm);

        return res;

    }

    public List<ChatDto> findRecentTwoChat(String userId) {
        log.info("userId: {}", userId);
        return chatRepository.findRecentTwoChat(userId);


    }
}
