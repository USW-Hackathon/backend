package com.ict.Hackathon.entity;



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
	private College college;
}
