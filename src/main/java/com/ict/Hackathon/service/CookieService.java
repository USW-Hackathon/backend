package com.ict.Hackathon.service;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

@Service
public class CookieService {

	private static final String REFRESH_TOKEN_NAME = "Refresh-Token";
	private static final int COOKIE_MAX_AGE = 3600; // 1시간 (초)

	public Cookie addRefreshTokenCookie( String token) {
		Cookie cookie = new Cookie(REFRESH_TOKEN_NAME, token);
		cookie.setPath("/");               // 모든 경로에 대해 쿠키 전송
		cookie.setHttpOnly(true);          // JS에서 접근 불가
		cookie.setSecure(true);            // HTTPS에서만 전송
		cookie.setMaxAge(COOKIE_MAX_AGE);  // 유효 시간 설정
		return cookie;        // 응답에 쿠키 추가
	}

	public Cookie deleteRefreshTokenCookie() {
		Cookie cookie = new Cookie(REFRESH_TOKEN_NAME, null);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setSecure(true);
		cookie.setMaxAge(0); // 즉시 만료
		return cookie;        // 응답에 쿠키 추가
	}
}
