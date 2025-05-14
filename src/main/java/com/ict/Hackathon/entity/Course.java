package com.ict.Hackathon.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "course")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "major_id", foreignKey = @ForeignKey(name = "fk_course_major"))
	@JsonIgnore
	private Major major;

	@Column
	private Integer grade;  // 학년

	@Column
	private Integer semester;  // 학기 (1, 2)

	@Column(name = "subject_code")
	private String subjectCode;  // 학수번호

	@Column(name = "completion_type")
	private String completionType;  // 이수구분

	private String name;  // 교과목명

	@Column
	private Integer credit;  // 학점

	@Column(name = "theory_hours")
	private Integer theoryHours;  // 이론시간

	@Column(name = "practice_hours")
	private Integer practiceHours;  // 실습시간

	@Column(name = "course_type")
	private String courseType;  // 과목구분

	@Column(columnDefinition = "TEXT")
	private String note;  // 비고
}
