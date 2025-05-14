package com.ict.Hackathon.repository;

import com.ict.Hackathon.entity.Course;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseRepository  extends CrudRepository<Course,Long> {

	List<Course> findAllByMajorIdAndGrade(Long majorId, Integer grade);
}
