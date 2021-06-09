package poly.chatservice.persistence.mongo;

import poly.chatservice.dto.ChatDto;

import java.util.List;

public interface IChatMapperUsingDto {

    public int insertChat(ChatDto chatDto, String colNm) throws Exception;
}
