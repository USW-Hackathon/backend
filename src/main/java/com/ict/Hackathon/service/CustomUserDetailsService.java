package com.ict.Hackathon.service;

import com.ict.Hackathon.entity.Member;
import com.ict.Hackathon.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

	private final MemberRepository memberRepository;

	@Override
	public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
		return memberRepository.findByMemberId(memberId)
			.map(this::createUserDetails)
			.orElseThrow(() -> new UsernameNotFoundException("해당하는 유저를 찾을 수 없습니다."));
	}

	private UserDetails createUserDetails(Member member) {
		// Member 자체가 UserDetails를 구현하고 있음
		return member;
	}
}
