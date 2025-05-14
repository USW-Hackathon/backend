package com.ict.Hackathon.repository;

import com.ict.Hackathon.entity.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository  extends CrudRepository<Course,Long> {

	Optional<List<Course>> findByMajorIdAndGrade(Long majorId, Integer grade);
}
