package com.ict.Hackathon.dto;


import com.ict.Hackathon.entity.College;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CollegeDto {


	private Long id;
	private String name;
	private String description;
	private List<MajorDto> majorName;

	public CollegeDto(College college, List<MajorDto> majorName) {
		this.id = college.getId();
		this.name = college.getName();
		this.description = college.getDescription();
		this.majorName = majorName;
	}
}
