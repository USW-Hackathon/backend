package com.ict.Hackathon.dto;


import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChatDto {
	private List<Map<String, String>> chatLog;
	private String LastMessage;

}
