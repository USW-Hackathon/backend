package com.ict.Hackathon.service;

import com.ict.Hackathon.config.GlobalExceptionHandler.InvalidTokenException;
import com.ict.Hackathon.dto.TokenResponseDto;
import com.ict.Hackathon.jwt.JwtTokenProvider;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtTokenProvider jwtTokenProvider;
	private final CookieService cookieService;

	public ResponseEntity<?> reissueToken(HttpServletRequest request,
		HttpServletResponse response) {
		String refreshToken = extractRefreshTokenFromCookies(request);

		if (refreshToken == null || !jwtTokenProvider.validateToken(refreshToken)) {
			throw new InvalidTokenException();
		}

		// accessToken에서 사용자 정보 추출
		String expiredAccessToken = jwtTokenProvider.resolveToken(request);
		String userEmail;

		try {
			userEmail = jwtTokenProvider.getAuthentication(expiredAccessToken).getName();
		} catch (Exception e) {
			userEmail = jwtTokenProvider.getSubjectFromRefreshToken(refreshToken);
		}

		Authentication authentication = new UsernamePasswordAuthenticationToken(
			userEmail, null, List.of(new SimpleGrantedAuthority("ROLE_USER"))
		);

		// 리프레시 재발급 시 만료시간 늘리기
		TokenResponseDto tokenDto = jwtTokenProvider.generateToken(authentication);

		// 응답 쿠키에 RefreshToken
		Cookie newRefreshCookie = cookieService.addRefreshTokenCookie(tokenDto.getRefreshToken());
		response.addCookie(newRefreshCookie);

		// 응답 헤더에 AccessToken
		response.setHeader("Authorization", "Bearer " + tokenDto.getAccessToken());

		return ResponseEntity.ok(Map.of("message", "토큰이 재발급되었습니다."));
	}


	private String extractRefreshTokenFromCookies(HttpServletRequest request) {
		if (request.getCookies() == null) {
			return null;
		}

		for (Cookie cookie : request.getCookies()) {
			if ("Refresh-Token".equals(cookie.getName())) {
				return cookie.getValue();
			}
		}
		return null;
	}
}
