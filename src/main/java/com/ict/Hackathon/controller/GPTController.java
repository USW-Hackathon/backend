package com.ict.Hackathon.controller;

import com.ict.Hackathon.dto.ChatDto;
import com.ict.Hackathon.service.ChatGPTService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class GPTController {

	private final ChatGPTService chatGPTService;

	@PostMapping
	public String askChatGpt(@RequestBody String Lastmeg) {
		String Prompt = chatGPTService.promptCheck(Lastmeg);
		return chatGPTService.send(Prompt);
	}
}
