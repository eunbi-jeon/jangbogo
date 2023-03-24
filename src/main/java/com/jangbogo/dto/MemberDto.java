package com.jangbogo.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class MemberDto {

	@Column(nullable = false, unique = true)
	private String email;

	@Column(nullable = false, unique = true)
	private String nickName;

	@Column(nullable = false)
	private String pass;

	private String loc; // 지역정보

}
