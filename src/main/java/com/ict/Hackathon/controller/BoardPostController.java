package com.ict.Hackathon.controller;

import com.ict.Hackathon.dto.BoardPostCreateRequestDto;
import com.ict.Hackathon.dto.BoardPostDto;
import com.ict.Hackathon.service.BoardPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board-posts")
@RequiredArgsConstructor
@Tag(name = "BoardPost", description = "학생 게시판 API")
public class BoardPostController {

	private final BoardPostService postService;

	@GetMapping
	@Operation(summary = "답글 포함 전체 게시글 조회 (10개 단위)")
	public Page<BoardPostDto> getPagedList(@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size) {
		Pageable pageable = PageRequest.of(page - 1, size); // 1번 페이지부터 요청
		return postService.getFlattenedPagedList(pageable);
	}

	@GetMapping("/{id}")
	@Operation(summary = "게시글 단건 조회 (조회수 증가)")
	public BoardPostDto getById(@PathVariable Long id) {
		return postService.getById(id);
	}

	@PostMapping
	@Operation(summary = "게시글 또는 답글 작성 (게시글 작성이면 parentId에 null 입력)")
	public void create(@RequestBody BoardPostCreateRequestDto requestDto) {
		postService.create(requestDto);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "게시글 삭제")
	public void delete(@PathVariable Long id) {
		postService.delete(id);
	}
}
