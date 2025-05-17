package com.ict.Hackathon.controller;

import com.ict.Hackathon.dto.BoardPostCreateRequestDto;
import com.ict.Hackathon.dto.BoardPostDto;
import com.ict.Hackathon.dto.BoardPostDtoList;
import com.ict.Hackathon.jwt.JwtTokenProvider;
import com.ict.Hackathon.service.BoardPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.security.sasl.AuthenticationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board-posts")
@RequiredArgsConstructor
@Tag(name = "BoardPost", description = "학생 게시판 API")
public class BoardPostController {

	private final BoardPostService postService;
	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping
	@Operation(summary = "답글 포함 전체 게시글 조회 (10개 단위) - (1:뉴스, 2:학생, 3:졸업작품)")
	public BoardPostDtoList getPagedList(@RequestParam(defaultValue = "1") int page,
		@RequestParam(defaultValue = "10") int size,@RequestParam("categoryId") int categoryId) {
		Pageable pageable = PageRequest.of(page - 1, size, Sort.by("groupId").descending());
		if(categoryId==0){
			return postService.getAllPagedList(pageable);

		}else{
			return postService.getFlattenedPagedList(pageable,categoryId);
		}
	}

	@GetMapping("/{id}")
	@Operation(summary = "게시글 단건 조회 (조회수 증가)")
	public BoardPostDto getById(@PathVariable Long id) {
		return postService.getById(id);
	}

	@PostMapping
	@Operation(summary = "게시글 또는 답글 작성 (게시글 작성이면 parentId에 null 입력)")
	public void create(@RequestBody BoardPostCreateRequestDto requestDto,@RequestHeader("Authorization") String token) {
		String pureToken = token.substring(7); // "Bearer " 제거

		if (!jwtTokenProvider.validateToken(pureToken)) {
			throw new BadCredentialsException("유효하지 않은 토큰입니다.");
		}

		postService.create(requestDto);
	}

	@DeleteMapping("/{id}")
	@Operation(summary = "게시글 삭제")
	public void delete(@PathVariable Long id,@RequestHeader("Authorization") String token) {
		String pureToken = token.substring(7); // "Bearer " 제거

		if (!jwtTokenProvider.validateToken(pureToken)) {
			throw new BadCredentialsException("유효하지 않은 토큰입니다.");
		}

		postService.delete(id);
	}
}
