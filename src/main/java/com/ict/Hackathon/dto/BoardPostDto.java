package com.ict.Hackathon.dto;

import com.ict.Hackathon.entity.BoardPost;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BoardPostDto {

	private final Long id;
	private final String title;
	private final String content;
	private final String writer;
	private final LocalDateTime createdAt;
	private final int viewCount;
	private final Long parentId;

	public static BoardPostDto from(BoardPost post) {
		return BoardPostDto.builder()
			.id(post.getId())
			.title(post.getTitle())
			.content(post.getContent())
			.writer(post.getWriter())
			.createdAt(post.getCreatedAt())
			.viewCount(post.getViewCount())
			.parentId(post.getParent() != null ? post.getParent().getId() : null)
			.build();
	}
}