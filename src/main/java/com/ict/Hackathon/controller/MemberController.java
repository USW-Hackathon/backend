package com.ict.Hackathon.controller;

import com.ict.Hackathon.dto.LoginRequestDto;
import com.ict.Hackathon.dto.TokenResponseDto;
import com.ict.Hackathon.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
@Tag(name = "Member", description = "회원 인증 관련 API")
public class MemberController {

	private final MemberService memberService;

	@PostMapping("/login")
	public ResponseEntity<Void> login(@RequestBody LoginRequestDto loginDto, HttpServletResponse response) {
		TokenResponseDto tokenResponse = memberService.login(loginDto.getMemberId(), loginDto.getPassword());

		response.setHeader("Authorization", "Bearer " + tokenResponse.getAccessToken());
		response.setHeader("Refresh-Token", tokenResponse.getRefreshToken());

		return ResponseEntity.ok().build();
	}
}