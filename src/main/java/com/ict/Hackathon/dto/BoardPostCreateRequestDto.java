package com.ict.Hackathon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class BoardPostCreateRequestDto {

	@Schema(example = "게시글 제목")
	private String title;

	@Schema(example = "게시글 내용")
	private String content;

	@Schema(example = "작성자 이름")
	private String writer;

	@Schema(description = "답글일 경우 원글 ID, 일반 게시글이면 null", example = "null", nullable = true)
	private Long parentId;
}