package com.ict.Hackathon.service;

import com.ict.Hackathon.dto.NoticeDto;
import com.ict.Hackathon.entity.Notice;
import com.ict.Hackathon.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class NoticeService {

	private final NoticeRepository noticeRepository;

	public Page<NoticeDto> getAll(Pageable pageable) {
		return noticeRepository.findAll(pageable)
			.map(NoticeDto::from);
	}

	@Transactional
	public NoticeDto getById(Long id) {
		Notice notice = noticeRepository.findById(id)
			.orElseThrow(() -> new IllegalArgumentException("해당 공지사항이 존재하지 않습니다."));

		notice.increaseViewCount();
		return NoticeDto.from(notice);
	}

	public Page<NoticeDto> getByCategory(int category, Pageable pageable) {
		return noticeRepository.findAllByCategory(category, pageable)
			.map(NoticeDto::from);
	}
}
