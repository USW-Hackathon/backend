package com.ict.Hackathon.service;

import com.ict.Hackathon.dto.BoardPostCreateRequestDto;
import com.ict.Hackathon.dto.BoardPostDto;
import com.ict.Hackathon.dto.BoardPostDtoList;
import com.ict.Hackathon.entity.BoardPost;
import com.ict.Hackathon.repository.BoardPostRepository;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardPostService {

	private final BoardPostRepository postRepository;

	@Transactional
	public BoardPostDto getById(Long id) {
		BoardPost post = postRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 글이 없습니다."));
		post.increaseViewCount();
		return BoardPostDto.from(post);
	}

	@Transactional
	public void create(BoardPostCreateRequestDto requestDto) {
		Long parentId = requestDto.getParentId();
		Long groupId = null;

		if (parentId != null) {
			groupId = postRepository.findById(parentId)
				.orElseThrow(() -> new IllegalArgumentException("부모 글이 존재하지 않습니다."))
				.getId();
		}

		BoardPost post = BoardPost.builder()
			.title(requestDto.getTitle())
			.content(requestDto.getContent())
			.categroryId(requestDto.getCategoryId())
			.writer(requestDto.getWriter())
			.createdAt(LocalDateTime.now())
			.viewCount(0)
			.groupId(groupId)  // 일단 null일 수도 있음
			.build();

		postRepository.save(post);

		// 부모 없으면 groupId를 자기 자신의 id로 갱신
		if (parentId == null) {
			post.ChangeGroupId();  // setter 사용
		}
	}


	public void delete(Long id) {
		if (!postRepository.existsById(id)) {
			throw new IllegalArgumentException("해당 글이 없습니다.");
		}
		postRepository.deleteById(id);
	}

	public BoardPostDtoList getFlattenedPagedList(Pageable pageable, int categoryId) {
		Page<BoardPost> allPosts = postRepository.findAllByCategoryId(categoryId,pageable);
		return new BoardPostDtoList(allPosts.getContent(),pageable.getPageNumber()+1,pageable.getPageSize(),allPosts.getTotalPages());
	}


}
