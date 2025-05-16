package com.ict.Hackathon.dto;

import com.ict.Hackathon.entity.Professor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ProfessorDto {

	private final Long id;
	private final String name;
	private final int division;
	private final int majorName;
	private final String research;
	private final String imageUrl;
	private final String email;
	private final String lab;
	private final String phone;

	public static ProfessorDto from(Professor entity) {
		return ProfessorDto.builder()
			.id(entity.getId())
			.name(entity.getName())
			.division(entity.getDivision())
			.majorName(entity.getMajorName())
			.research(entity.getResearch())
			.imageUrl(entity.getImageUrl())
			.email(entity.getEmail())
			.lab(entity.getLab())
			.phone(entity.getPhone())
			.build();
	}
}