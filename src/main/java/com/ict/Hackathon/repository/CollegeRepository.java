package com.ict.Hackathon.repository;


import com.ict.Hackathon.dto.CollegeDto;
import com.ict.Hackathon.entity.College;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CollegeRepository extends JpaRepository<College, Long> {
}
