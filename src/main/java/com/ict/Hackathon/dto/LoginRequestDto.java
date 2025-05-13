package com.ict.Hackathon.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginRequestDto {
	private String memberId;
	private String password;
}
