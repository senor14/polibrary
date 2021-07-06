package poly.chatservice.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import poly.chatservice.dto.ChatDto;
//import poly.chatservice.dto.ChatRoomDto;
//import poly.chatservice.jpa.ChatsRoomRepository;
import poly.chatservice.dto.ChatRoomDto;
import poly.chatservice.jpa.ChatsRoomRepository;
import poly.chatservice.persistence.mongo.IChatMapperUsingDto;
import poly.chatservice.poly.DateUtil;
import poly.chatservice.repo.ChatRoomRepository;
import poly.chatservice.vo.ChatResponse;
import poly.chatservice.vo.ChatRoomResponse;

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

    public ChatRoomDto getExistRooms(ChatRoomDto chatRoomDto) {

        ChatRoomDto result = chatRoomRepository.existRoom(chatRoomDto);

        return result;
    }

}
