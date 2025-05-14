package com.ict.Hackathon.controller;

import com.ict.Hackathon.dto.CollegeDto;
import com.ict.Hackathon.entity.Course;
import com.ict.Hackathon.entity.Major;
import com.ict.Hackathon.service.CollegeService;
import com.ict.Hackathon.service.MajorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/major")
@RequiredArgsConstructor
@Tag(name = "Major", description = "")
public class MajorController {

	private final MajorService majorService;
	//
	@Operation(summary = "학과정보")
	@GetMapping("/{majorId}")
	public ResponseEntity majorInform(@PathVariable Long majorId) {
		Major major = majorService.majorInform(majorId);
		return ResponseEntity.ok(major);
	}


}