package poly.postservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.stereotype.Service;
import poly.postservice.dto.PostDto;
import poly.postservice.persistence.jpa.PostEntity;
import poly.postservice.persistence.jpa.PostRepository;


import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {


	private final PostRepository postRepository;

	public PostDto insertPost(PostDto pDto) {

		ModelMapper mapper = new ModelMapper();
		PostEntity postEntity = mapper.map(pDto, PostEntity.class);

		postRepository.save(postEntity);

		PostDto returnPostDto = mapper.map(postEntity, PostDto.class);

		return returnPostDto;
	}

	public Iterable<PostEntity> getPostList() {

		return postRepository.findAll();
	}

	public PostEntity getPostDetail(Long postId) {
		return postRepository.findByPostId(postId);
	}


	public List<PostEntity> userReqList(String userUuid) {
		List<PostEntity> allByUserUuidOrderByRegDtDesc = postRepository.findAllByUserUuidOrderByRegDtDesc(userUuid);
		return allByUserUuidOrderByRegDtDesc;
	}

	public int deletePost(Long postId) {
		return postRepository.deleteByPostId(postId);
	}

	public PostDto updateReqStatus(Long postId, Integer status) {

		ModelMapper mapper = new ModelMapper();

		PostEntity postEntity = postRepository.findByPostId(postId);
		postEntity.setStatus(status);
		postRepository.save(postEntity);

		PostDto postDto = mapper.map(postEntity, PostDto.class);

		return postDto;
	}

	public PostDto updatePostInfo(Long postId, PostDto postDto) {
		PostEntity postEntity = postRepository.findByPostId(postId);

		ModelMapper mapper = new ModelMapper();
		mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

		mapper.map(postDto, postEntity);

		postRepository.save(postEntity);

		mapper.map(postEntity, postDto);

		return postDto;
	}


}
