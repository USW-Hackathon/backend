package com.ict.Hackathon.service;


import com.ict.Hackathon.entity.Course;
import com.ict.Hackathon.repository.CourseRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CourseService {

	private final CourseRepository courseRepository;

	public List<Course> majorCourse(Long majorId, Integer grade) {
		return courseRepository.findAllByMajorIdAndGrade(majorId, grade);
	}
}
