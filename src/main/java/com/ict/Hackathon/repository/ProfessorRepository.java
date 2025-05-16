package com.ict.Hackathon.repository;

import com.ict.Hackathon.entity.Professor;
import java.util.Arrays;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfessorRepository extends JpaRepository<Professor, Long> {

	List<Professor> findByDivisionAndMajorName(int division, int majorName);

	List<Professor> findByDivision(int division);
}