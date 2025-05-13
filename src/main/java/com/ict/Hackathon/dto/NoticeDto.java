package com.ict.Hackathon.dto;

import com.ict.Hackathon.entity.Notice;
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

	public static NoticeDto from(Notice notice) {
		return NoticeDto.builder()
			.id(notice.getId())
			.title(notice.getTitle())
			.content(notice.getContent())
			.writer(notice.getWriter())
			.createdAt(notice.getCreatedAt())
			.viewCount(notice.getViewCount())
			.build();
	}
}