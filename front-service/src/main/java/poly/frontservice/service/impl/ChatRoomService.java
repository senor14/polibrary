package poly.frontservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.frontservice.dto.ChatDto;
import poly.frontservice.dto.ChatRoomDto;
import poly.frontservice.jpa.ChatsRoomRepository;
import poly.frontservice.persistence.mongo.IChatMapperUsingDto;
import poly.frontservice.util.DateUtil;
import poly.frontservice.repo.ChatRoomRepository;
import poly.frontservice.vo.ChatResponse;
import poly.frontservice.vo.ChatRoomResponse;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
@Service
public class ChatRoomService {

    private final ChatsRoomRepository chatsRoomRepository;
    private final ChatRoomRepository chatRoomRepository;


    // chatting room create
    @Transactional
    public ChatRoomResponse createRoom(ChatRoomDto chatRoomDto) {
        chatRoomDto.setRoomId(UUID.randomUUID().toString());
        chatRoomDto = chatsRoomRepository.save(chatRoomDto);

        ModelMapper mapper = new ModelMapper();
        ChatRoomResponse chatRoomResponse = mapper.map(chatRoomDto, ChatRoomResponse.class);

        return chatRoomResponse;
    }



    // 요청방 가져오기
    public List<ChatRoomDto> getReqRooms(String userId) {

        List<ChatRoomDto> rList = chatRoomRepository.findReqRoom(userId);

        return rList;
    }

    public List<ChatRoomDto> getDelRooms(String userId) {

        List<ChatRoomDto> rList = chatRoomRepository.findDelRoom(userId);

        return rList;
    }

}
