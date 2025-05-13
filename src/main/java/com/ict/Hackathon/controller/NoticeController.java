package com.ict.Hackathon.controller;

import com.ict.Hackathon.dto.NoticeDto;
import com.ict.Hackathon.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notices")
@RequiredArgsConstructor
@Tag(name = "Notice", description = "공지사항 API")
public class NoticeController {

	private final NoticeService noticeService;

	@GetMapping
	@Operation(summary = "공지사항 전체 조회")
	public List<NoticeDto> getAllNotices() {
		return noticeService.getAll();
	}

	@GetMapping("/{id}")
	@Operation(summary = "공지사항 단건 조회")
	public NoticeDto getNoticeById(@PathVariable Long id) {
		return noticeService.getById(id);
	}
}
