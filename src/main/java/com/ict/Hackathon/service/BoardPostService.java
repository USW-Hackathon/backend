package com.ict.Hackathon.service;

import com.ict.Hackathon.dto.BoardPostCreateRequestDto;
import com.ict.Hackathon.dto.BoardPostDto;
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
		BoardPost parent = null;
		if (requestDto.getParentId() != null) {
			parent = postRepository.findById(requestDto.getParentId())
				.orElseThrow(() -> new IllegalArgumentException("부모 글이 존재하지 않습니다."));
		}

		BoardPost post = BoardPost.builder()
			.title(requestDto.getTitle())
			.content(requestDto.getContent())
			.writer(requestDto.getWriter())
			.createdAt(LocalDateTime.now())
			.viewCount(0)
			.parent(parent)
			.build();

		postRepository.save(post);
	}


	public void delete(Long id) {
		if (!postRepository.existsById(id)) {
			throw new IllegalArgumentException("해당 글이 없습니다.");
		}
		postRepository.deleteById(id);
	}

	public Page<BoardPostDto> getFlattenedPagedList(Pageable pageable) {
		List<BoardPost> allPosts = postRepository.findAll(Sort.by(Sort.Order.desc("createdAt")));

		// 원글-답글 정렬된 리스트 구성
		List<BoardPostDto> flatList = new ArrayList<>();
		Map<Long, List<BoardPost>> replyMap = allPosts.stream()
			.filter(p -> p.getParent() != null)
			.collect(Collectors.groupingBy(p -> p.getParent().getId()));

		allPosts.stream()
			.filter(p -> p.getParent() == null)
			.forEach(parent -> {
				flatList.add(BoardPostDto.from(parent)); // 원글 추가
				List<BoardPost> replies = replyMap.getOrDefault(parent.getId(), List.of());
				replies.stream()
					.sorted(Comparator.comparing(BoardPost::getCreatedAt))
					.map(BoardPostDto::from)
					.forEach(flatList::add); // 답글 추가
			});

		// 페이징 처리 (flatList 기준으로 잘라내기)
		int start = (int) pageable.getOffset();
		int end = Math.min(start + pageable.getPageSize(), flatList.size());
		List<BoardPostDto> pageContent = flatList.subList(start, end);

		return new PageImpl<>(pageContent, pageable, flatList.size());
	}


}
