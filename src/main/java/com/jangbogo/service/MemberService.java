package com.jangbogo.service;

import com.jangbogo.DataNotFoundException;
import com.jangbogo.domain.Member;
import com.jangbogo.dto.MemberDto;
import com.jangbogo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Optional;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    public Member saveMember(MemberDto memberDto) {
        Member member = new Member(memberDto.getEmail(), memberDto.getNickName(), memberDto.getPass(), memberDto.getLoc());
        return memberRepository.save(member);
    }

    /* 회원가입 중복 체크 */

    public boolean checkemail(String email) {
        log.info("memberService : 이메일 중복확인 체크 시작");
        return memberRepository.existsByEmail(email);
    }
    public boolean checkNickname(String nickname) {
        log.info("memberService : 닉네임 중복확인 체크 시작");
        return memberRepository.existsByNickName(nickname);
    }
    
    public Member getMember(String nickName) {
    	
    	Optional<Member> member = this.memberRepository.findBynickName(nickName);
    	
    	if(member.isPresent()) {
    		return member.get();
    	}else {
    		throw new DataNotFoundException("해당 사용자는 존재하지 않습니다.");
    	}
    }
}
