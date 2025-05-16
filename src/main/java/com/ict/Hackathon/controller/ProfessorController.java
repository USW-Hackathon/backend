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
		@Parameter(
			description = """
        전공 번호로 조회합니다.  
        1: 컴퓨터학부
        2: 정보통신학부 
        3: 데이터과학부
        4: 클라우드융복합
        """, example = "1"
		)
		@RequestParam(required = false) String division,

		@Parameter(
			description = """
        전공 번호로 조회합니다.  
        1: 컴퓨터SW  
        2: 미디어SW  
        3: 정보보호  
        4: 정보통신  
        5: 데이터과학  
        6: 클라우드융합
        """, example = "1"
		)
		@RequestParam(required = false) String major) {

		if (division != null && major != null) {
			return professorService.getByDivisionAndMajor(division, major);
		}
		return professorService.getAll();
	}
}