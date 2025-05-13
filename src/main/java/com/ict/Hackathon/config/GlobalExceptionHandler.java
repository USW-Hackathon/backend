package com.ict.Hackathon.config;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// 공통적인 예외 응답을 반환하는 메서드
	private ResponseEntity<Map<String, String>> buildErrorResponse(String message,
		HttpStatus status) {
		Map<String, String> errorResponse = new HashMap<>();
		errorResponse.put("message", message);
		return ResponseEntity.status(status).body(errorResponse);
	}

	// 토큰이 없을 경우 예외 클래스 정의
	@ExceptionHandler(MissingTokenException.class)
	public ResponseEntity<Map<String, String>> handleMissingTokenException(
		MissingTokenException e) {
		return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// 유효하지 않은 토큰일 경우 예외 클래스 정의
	@ExceptionHandler(InvalidTokenException.class)
	public ResponseEntity<Map<String, String>> handleInvalidTokenException(
		InvalidTokenException e) {
		return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// 관리자 권한이 없을 경우 예외 클래스 정의
	@ExceptionHandler(AdminAccessDeniedException.class)
	public ResponseEntity<Map<String, String>> handleAdminAccessDeniedException(
		AdminAccessDeniedException e) {
		return buildErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN);
	}

	// 로그인 실패 (잘못된 아이디/비밀번호)
	@ExceptionHandler(InvalidLoginException.class)
	public ResponseEntity<Map<String, String>> handleInvalidLoginException(
		InvalidLoginException e) {
		return buildErrorResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
	}

	// 사용자를 찾을 수 없을 경우 예외 클래스 정의
	@ExceptionHandler(MemberNotFoundException.class)
	public ResponseEntity<Map<String, String>> handleMemberNotFoundException(
		MemberNotFoundException e) {
		return buildErrorResponse(e.getMessage(), HttpStatus.FORBIDDEN);
	}

	// 비밀번호가 일치하지 않을 경우 예외 처리
	@ExceptionHandler(PasswordMismatchException.class)
	public ResponseEntity<Map<String, String>> handlePasswordMismatchException(
		PasswordMismatchException e) {
		return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	//IllegalArgumentException 발생 시 처리
	@ExceptionHandler(IllegalArgumentException.class)
	public ResponseEntity<Map<String, String>> handleIllegalArgumentException(
		IllegalArgumentException e) {
		return buildErrorResponse(e.getMessage(), HttpStatus.BAD_REQUEST);
	}

	// 기타 예외 (MimeMessage 생성 오류 포함) 처리
	@ExceptionHandler(RuntimeException.class)
	public ResponseEntity<Map<String, String>> handleRuntimeException(RuntimeException e) {
		return buildErrorResponse("서버 내부 오류 발생: " + e.getMessage(),
			HttpStatus.INTERNAL_SERVER_ERROR);
	}

	public static class PasswordMismatchException extends RuntimeException {

		public PasswordMismatchException() {
			super("비밀번호가 일치하지 않습니다.");
		}
	}

	public static class MissingTokenException extends RuntimeException {

		public MissingTokenException() {
			super("요청에 토큰이 포함되어 있지 않습니다.");
		}
	}

	public static class InvalidTokenException extends RuntimeException {

		public InvalidTokenException() {
			super("유효하지 않은 토큰입니다.");
		}
	}

	public static class AdminAccessDeniedException extends RuntimeException {

		public AdminAccessDeniedException() {
			super("관리자 계정이 아닙니다.");
		}
	}

	public static class MemberNotFoundException extends RuntimeException {

		public MemberNotFoundException() {
			super("존재하지 않는 계정입니다.");
		}
	}


	public static class InvalidLoginException extends RuntimeException {

		public InvalidLoginException() {
			super("아이디 또는 비밀번호가 잘못되었습니다.");
		}
	}
}