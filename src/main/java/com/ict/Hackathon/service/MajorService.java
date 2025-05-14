package com.ict.Hackathon.service;


import com.ict.Hackathon.entity.Major;
import com.ict.Hackathon.repository.MajorRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Getter
public class MajorService {

	private final MajorRepository majorRepository;

	public Major majorInform(Long majorId) {
		Major major = majorRepository.findById(majorId).get();
		return major;
	}


}
