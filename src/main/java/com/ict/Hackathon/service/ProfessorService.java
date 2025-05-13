package com.ict.Hackathon.service;

import com.ict.Hackathon.dto.ProfessorDto;
import com.ict.Hackathon.repository.ProfessorRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfessorService {

	private final ProfessorRepository professorRepository;

	public List<ProfessorDto> getAll() {
		return professorRepository.findAll().stream()
			.map(ProfessorDto::from)
			.collect(Collectors.toList());
	}

	public List<ProfessorDto> getByDivisionAndMajor(String division, String majorName) {
		return professorRepository.findByDivisionAndMajorName(division, majorName).stream()
			.map(ProfessorDto::from)
			.collect(Collectors.toList());
	}
}