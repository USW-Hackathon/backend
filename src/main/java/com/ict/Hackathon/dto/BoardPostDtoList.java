package com.ict.Hackathon.dto;

import com.ict.Hackathon.entity.BoardPost;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BoardPostDtoList {
	private List<BoardPost> boardPostList;
	private int size;
	private int page;
	private int total;
}
