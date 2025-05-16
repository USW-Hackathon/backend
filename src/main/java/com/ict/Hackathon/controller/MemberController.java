package com.ict.Hackathon.controller;

import com.ict.Hackathon.dto.LoginRequestDto;
import com.ict.Hackathon.dto.TokenResponseDto;
import com.ict.Hackathon.service.CookieService;
import com.ict.Hackathon.service.MemberService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
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
	private final CookieService cookieService;

	@PostMapping("/login")
	public ResponseEntity<Map<String, String>> login(@RequestBody LoginRequestDto loginDto,
		HttpServletResponse response) {
		TokenResponseDto tokenResponse = memberService.login(loginDto.getMemberId(),
			loginDto.getPassword());

		// accessToken을 헤더에 추가
		response.setHeader("Authorization", "Bearer " + tokenResponse.getAccessToken());

		// refreshToken은 쿠키에 추가
		response.addCookie(cookieService.addRefreshTokenCookie(tokenResponse.getRefreshToken()));

		// userName만 body로 반환
		Map<String, String> body = new HashMap<>();
		body.put("userName", tokenResponse.getUserName());

		return ResponseEntity.ok(body);
	}

}