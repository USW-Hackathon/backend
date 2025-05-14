package com.ict.Hackathon.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notice")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Notice {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	private String writer;

	private LocalDateTime createdAt;

	private int viewCount;

	@Column(nullable = false)
	private int category; // 1: 취업, 2: 학과, 3: 대학원

	public void increaseViewCount() {
		this.viewCount++;
	}
}
