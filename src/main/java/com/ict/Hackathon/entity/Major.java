package com.ict.Hackathon.entity;



import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "major")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Major {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false, length = 100)
	private String name;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "college_id", foreignKey = @ForeignKey(name = "fk_major_college"))
	@JsonIgnore
	private College college;

	@Column(columnDefinition = "TEXT")
	private String introduction;

	private String location;

	private String phone;

	private String fax;

	@Column(name = "office_hours")
	private String officeHours;

	@Column(columnDefinition = "TEXT")
	private String future;

	@Column(name = "special_programs",columnDefinition = "TEXT")
	private String specialPrograms;

	private String clubs;

	private String career;

	private String certifications;

	@Column(name = "research_center")
	private String researchCenter;
}
