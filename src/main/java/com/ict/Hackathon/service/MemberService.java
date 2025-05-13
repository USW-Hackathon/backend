package com.ict.Hackathon.service;

import com.ict.Hackathon.config.GlobalExceptionHandler.InvalidLoginException;
import com.ict.Hackathon.config.GlobalExceptionHandler.MemberNotFoundException;
import com.ict.Hackathon.dto.TokenResponseDto;
import com.ict.Hackathon.entity.Member;
import com.ict.Hackathon.jwt.JwtTokenProvider;
import com.ict.Hackathon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

	private final MemberRepository memberRepository;
	private final PasswordEncoder passwordEncoder;
	private final AuthenticationManager authenticationManager;
	private final JwtTokenProvider jwtTokenProvider;

	@Transactional
	public TokenResponseDto login(String memberId, String password) {
		// 1. 사용자 존재 확인
		Member member = memberRepository.findByMemberId(memberId)
			.orElseThrow(MemberNotFoundException::new);

		// 2. 비밀번호 확인
		if (!passwordEncoder.matches(password, member.getPassword())) {
			throw new InvalidLoginException();
		}

		// 3. 인증 객체 생성 후 토큰 생성
		Authentication authentication = authenticationManager.authenticate(
			new UsernamePasswordAuthenticationToken(memberId, password));

		return jwtTokenProvider.generateToken(authentication);
	}
}
