package com.ict.Hackathon.dto;

import com.ict.Hackathon.entity.Notice;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class NoticeDto {

	private final Long id;

	private final String title;

	private final String content;

	private final String writer;

	private final LocalDateTime createdAt;

	private final int viewCount;
	
	@Schema(description = "카테고리 (1: 취업, 2: 학과, 3: 대학원)")
	private final int category;

	public static NoticeDto from(Notice notice) {
		return NoticeDto.builder()
			.id(notice.getId())
			.title(notice.getTitle())
			.content(notice.getContent())
			.writer(notice.getWriter())
			.createdAt(notice.getCreatedAt())
			.viewCount(notice.getViewCount())
			.category(notice.getCategory())
			.build();
	}
}