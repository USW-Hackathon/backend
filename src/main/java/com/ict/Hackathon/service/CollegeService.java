package com.ict.Hackathon.service;


import com.ict.Hackathon.dto.CollegeDto;
import com.ict.Hackathon.dto.MajorDto;
import com.ict.Hackathon.entity.College;
import com.ict.Hackathon.repository.CollegeRepository;
import com.ict.Hackathon.repository.MajorRepository;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CollegeService {
	private final CollegeRepository collegeRepository;
	private final MajorRepository majorRepository;

	public CollegeDto findCollege(Long collegeId) {
		College college = collegeRepository.findById(collegeId).orElseThrow(()-> new IllegalArgumentException());
		List<MajorDto> majorName = majorRepository.findByCollege(collegeId);
		return new CollegeDto(college,majorName);
	}
}
