package com.ict.Hackathon.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "professor")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Professor {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;
	private int division;     // 예: 컴퓨터학부
	private int majorName;    // 예: 컴퓨터SW, 미디어SW

	@Column(columnDefinition = "TEXT")
	private String research;     // 전공 내용

	@Column(columnDefinition = "TEXT",name = "image_url")
	private String imageUrl;

	private String email;
	private String lab;
	private String phone;
}