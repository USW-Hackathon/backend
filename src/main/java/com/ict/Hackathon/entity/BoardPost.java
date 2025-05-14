package com.ict.Hackathon.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "board_post")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class BoardPost {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String title;

	@Column(columnDefinition = "TEXT")
	private String content;

	@Column(name = "category_id")
	private int categroryId;

	private String writer;

	private LocalDateTime createdAt;

	private int viewCount;

	@Column(name = "group_id")
	private Long groupId;

	public void increaseViewCount() {
		this.viewCount++;
	}

	public void  ChangeGroupId() {
		this.groupId = this.id;
	}
}