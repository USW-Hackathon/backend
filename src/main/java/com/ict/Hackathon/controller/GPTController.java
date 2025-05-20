package com.ict.Hackathon.controller;

import com.ict.Hackathon.dto.ChatDto;
import com.ict.Hackathon.service.ChatGPTService;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import org.springframework.web.client.HttpClientErrorException.BadRequest;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
@Slf4j
public class GPTController {

	private final ChatGPTService chatGPTService;

	@PostMapping
	public String askChatGpt(@RequestBody String Lastmeg) {
		if(Lastmeg.length() >= 100) {
			return "질문은 100자 아래로만 가능합니다. 다시요청해주세요";
		}
		String Prompt = chatGPTService.promptCheck(Lastmeg);
		List<String> keywords = Arrays.asList("학과", "학부","전공", "교수");

		boolean matched = keywords.stream().anyMatch(Prompt::contains);

		if (matched) {
			return chatGPTService.sendUni(Prompt);
		} else {
			return chatGPTService.sendEtc(Prompt);
		}
	}
}
