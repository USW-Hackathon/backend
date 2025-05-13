package com.ict.Hackathon.controller;

import com.ict.Hackathon.dto.ProfessorDto;
import com.ict.Hackathon.service.ProfessorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professors")
@RequiredArgsConstructor
@Tag(name = "Professor", description = "교수 정보 조회 API")
public class ProfessorController {

	private final ProfessorService professorService;

	@GetMapping
	@Operation(summary = "교수 목록 조회", description = "division(학부), major(전공)으로 필터링된 교수 목록을 조회합니다.")
	public List<ProfessorDto> getProfessors(
		@Parameter(description = "학부 이름", example = "컴퓨터학부")
		@RequestParam(required = false) String division,

		@Parameter(description = "전공 이름", example = "컴퓨터SW")
		@RequestParam(required = false) String major) {

		if (division != null && major != null) {
			return professorService.getByDivisionAndMajor(division, major);
		}
		return professorService.getAll();
	}
}