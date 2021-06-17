package poly.frontservice.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import poly.frontservice.dto.ChatDto;
import poly.frontservice.jpa.ChatsRoomRepository;
import poly.frontservice.util.DateUtil;
import poly.frontservice.repo.ChatRepository;
import poly.frontservice.repo.ChatRoomRepository;

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
}
