package com.ict.Hackathon.controller;

import com.ict.Hackathon.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
@Tag(name = "Auth", description = "인증 API")
public class AuthController {

	private final AuthService authService;

	@PostMapping("/reissue")
	@Operation(summary = "액세스 토큰 재발급", description = "Refresh 토큰을 통해 새로운 Access 토큰을 재발급합니다.")
	public ResponseEntity<?> reissueToken(HttpServletRequest request,
		HttpServletResponse response) {
		return authService.reissueToken(request, response);
	}
}
