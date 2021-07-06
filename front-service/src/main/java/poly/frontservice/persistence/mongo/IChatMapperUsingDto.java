package poly.frontservice.persistence.mongo;

import poly.frontservice.dto.ChatDto;

import java.util.List;

public interface IChatMapperUsingDto {

    public int insertChat(ChatDto chatDto, String colNm) throws Exception;
}
