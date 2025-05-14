package com.ict.Hackathon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "college")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class College {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "description", columnDefinition = "TEXT")
	private String description;
}