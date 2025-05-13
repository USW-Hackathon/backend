package com.ict.Hackathon.repository;

import com.ict.Hackathon.entity.Member;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

	// MemberId를 기준으로 회원을 찾는 메서드
	Optional<Member> findByMemberId(String memberId);

}