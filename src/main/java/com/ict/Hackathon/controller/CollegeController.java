package com.ict.Hackathon.controller;


import com.ict.Hackathon.dto.CollegeDto;
import com.ict.Hackathon.service.CollegeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/college")
@RequiredArgsConstructor
@Tag(name = "College", description = "")
public class CollegeController {
	private final CollegeService collegeService;

	//
	@Operation(summary = "단과대학정보")
	@GetMapping("{college_id}")
	public ResponseEntity findCollegeById(@PathVariable Long college_id) {
		CollegeDto collegeDto =collegeService.findCollege(college_id);
		return ResponseEntity.ok(collegeDto);
	}
}
