package com.ict.Hackathon.repository;


import com.ict.Hackathon.dto.MajorDto;
import com.ict.Hackathon.entity.Major;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major,Long> {

	@Query("select  new com.ict.Hackathon.dto.MajorDto(m.id, m.name) FROM Major m where m.college.id = :collegeId")
	List<MajorDto> findByCollege(@Param("collegeId") Long collegeId);
}
