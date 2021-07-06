package poly.postservice.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import poly.postservice.dto.DateUtil;
import poly.postservice.dto.PostDto;
import poly.postservice.persistence.jpa.PostEntity;
import poly.postservice.persistence.jpa.PostRepository;
import poly.postservice.service.PostService;
import poly.postservice.vo.RequestCreatePost;
import poly.postservice.vo.RequestPost;
import poly.postservice.vo.ResponsePost;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/post-service")
public class PostController {

	private final PostService postService;
	private final PostRepository postRepository;
	
	@GetMapping("/posts")
	public ResponseEntity<List<ResponsePost>> postList() {
		Iterable<PostEntity> postList = postService.getPostList();

		List<ResponsePost> result = new ArrayList<>();

		postList.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponsePost.class));
		});

		
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
	
	
	@PostMapping("/posts")
	public ResponseEntity<ResponsePost> doPost(@RequestBody RequestCreatePost requestCreatePost) {

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		PostDto pDTO = mapper.map(requestCreatePost, PostDto.class);
		pDTO.setRegDt(DateUtil.getDateTime("yyyyMMddhhmmss"));
		PostDto postDto = postService.insertPost(pDTO);

		ResponsePost responsePost = mapper.map(postDto, ResponsePost.class);

		return ResponseEntity.status(HttpStatus.CREATED).body(responsePost);

	}

	// 유저 요청글 리스트
	@GetMapping("/posts/{userId}/requests")
	public ResponseEntity<List<ResponsePost>> userReqList(@PathVariable("userId") String userUuid) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<PostEntity> postEntities  = postService.userReqList(userUuid);
		List<ResponsePost> result = new ArrayList();
		postEntities.forEach(v -> {
			result.add(new ModelMapper().map(v, ResponsePost.class));
		});
		try {
			return ResponseEntity.status(HttpStatus.OK).body(result);
		} catch (Exception e) {
			throw e;
		}
	}

	// 요청글 상세
	@GetMapping("/posts/{postId}")
	public ResponseEntity<ResponsePost> boardDetail(@PathVariable Long postId) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		PostEntity postDetail = postService.getPostDetail(postId);

		try {
			ResponsePost responsePost = mapper.map(postDetail, ResponsePost.class);
			return ResponseEntity.status(HttpStatus.OK).body(responsePost);
		} catch (Exception e) {
			throw e;
		}

	}

//	@GetMapping("/posts/{postId}/edit")
//	public ResponseEntity<ResponsePost> editPost(@PathVariable Long postId,
//												 @RequestBody RequestPost requestPost) {
//		ModelMapper mapper = new ModelMapper();
//		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
//
//		PostEntity result = postService.getPostDetail(postId);
//
//
//
//		try {
//			ResponsePost responsePost = mapper.map(result, ResponsePost.class);
//			return ResponseEntity.status(HttpStatus.OK).body(responsePost);
//		} catch (Exception e) {
//			throw e;
//		}
//
//	}
	
//	@PutMapping("/posts/{postId}")
//	public ResponseEntity<ResponsePost> doEditPost(@RequestBody RequestPost requestPost) {
//
//		ModelMapper mapper = new ModelMapper();
//
//		PostDto postDto = mapper.map(requestPost, PostDto.class);
//
//		int res = postService.updatePost(postDto);
//
//
//		try {
//			ResponsePost responsePost = mapper.map(postDto, ResponsePost.class);
//			return ResponseEntity.status(HttpStatus.OK).body(responsePost);
//		} catch (Exception e) {
//			throw e;
//		}
//
//	}
	
	// 요청글 삭제
	@DeleteMapping("/posts/{postId}")
	public ResponseEntity<Integer> deletePost(@PathVariable Long postId) {
		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		int res = postService.deletePost(postId);
		try {
			return ResponseEntity.status(HttpStatus.OK).body(res);
		} catch (Exception e) {
			throw e;
		}
		
	}

	// 요청글 수정
	@PutMapping("/posts/{postId}")
	public ResponseEntity<ResponsePost> updatePost(@PathVariable Long postId,
											  @RequestBody PostDto postDto) {

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		PostDto resultDto = postService.updatePostInfo(postId, postDto);

		ResponsePost result = mapper.map(resultDto, ResponsePost.class);

		return ResponseEntity.status(HttpStatus.OK).body(result);

	}


	// 요청글 상태 변경
	@PutMapping("/posts/{postId}/status/{status}")
	public ResponseEntity<ResponsePost> changeRentalStatus(@PathVariable Long postId,
															 @PathVariable Integer status) {

		ModelMapper mapper = new ModelMapper();


		PostDto postDto = postService.updateReqStatus(postId, status);

		ResponsePost result = mapper.map(postDto, ResponsePost.class);

		return ResponseEntity.status(HttpStatus.OK).body(result);

	}

	@GetMapping("/posts/{userId}/status/2")
	public ResponseEntity<List<ResponsePost>> getSuccessBooks(@PathVariable String userId) {

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		List<PostEntity> successPosts = postRepository.findSuccessPosts(userId);

		List<ResponsePost> result = new ArrayList<>();

		successPosts.forEach(v -> {
			result.add(mapper.map(v, ResponsePost.class));
		});

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	@GetMapping("/posts/users/{userId}/books/{bookId}")
	public ResponseEntity<Long> getUserBookPostIds(@PathVariable String userId,
												   @PathVariable Long bookId) {

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		Long userBookPosts = postRepository.userBookPostIds(userId, bookId);

//		Long result = new ArrayList<>();

//		userBookPosts.forEach(v -> {
//			result.add(mapper.map(v, Long.class));
//		});

		return ResponseEntity.status(HttpStatus.OK).body(userBookPosts);
	}
}

