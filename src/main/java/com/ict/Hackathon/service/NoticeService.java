package com.ict.Hackathon.service;

import com.ict.Hackathon.dto.NoticeDto;
import com.ict.Hackathon.entity.Notice;
import com.ict.Hackathon.repository.NoticeRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	public List<NoticeDto> getAll() {
		return noticeRepository.findAll().stream()
			.map(NoticeDto::from)
			.collect(Collectors.toList());
	}

	@Transactional
	public NoticeDto getById(Long id) {
		Notice notice = noticeRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));

		notice.increaseViewCount();
		return NoticeDto.from(notice);
	}
}
