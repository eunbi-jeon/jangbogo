package com.jangbogo.service;


import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.exeption.DataNotFoundException;
import com.jangbogo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    
    public Member getMember(String username) {
        Optional<Member> member = this.memberRepository.findByEmail(username);
        
        if (member.isPresent()) {  //Optional 에 SiteUser가 검색되면 (존재하면) 
        	
            return member.get();
            
        } else {
            throw new DataNotFoundException("siteuser not found - 해당 사용자는 존재하지 않습니다. ");
        }
    }
}