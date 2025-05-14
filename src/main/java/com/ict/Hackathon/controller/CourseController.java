package com.ict.Hackathon.controller;


import com.ict.Hackathon.entity.Course;
import com.ict.Hackathon.service.CourseService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name = "Course", description = "")
public class CourseController {
	private final CourseService courseService;

	@Operation(summary = "교과과정")
	@GetMapping("/course")
	public ResponseEntity<Map<String, Object>> majorCourse(@RequestParam("majorId") Long majorId, @RequestParam("grade") Integer grade) {
		List<Course> courses = courseService.majorCourse(majorId,grade);
		Map<String,Object> map = new HashMap<>();
		map.put("courses",courses);
		return ResponseEntity.ok(map);
	}

}
