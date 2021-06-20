package poly.postservice.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class PostDto {
	private Long postId;
	private String userUuid;
	private String nickname;
	private String department;
	private Long bookId;
	private String title;
	private String author;
	private String thumbnail;
	private String regDt;
	private Integer status;

}
