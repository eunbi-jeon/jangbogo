package com.jangbogo.service;

import com.jangbogo.DataNotFoundException;
import com.jangbogo.domain.member.entity.Member;

import com.jangbogo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Service;

import java.nio.file.attribute.UserPrincipal;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;


//    public ResponseEntity<?> readByUser(UserPrincipal userPrincipal){
//        Optional<Member> user = memberRepository.findById(userPrincipal.getId());
//        DefaultAssert.isOptionalPresent(user);
//        ApiResponse apiResponse = ApiResponse.builder().check(true).information(user.get()).build();
//        return ResponseEntity.ok(apiResponse);
//    }
    
    public Member getMember(String username) {
    	
    	
        Optional<Member> member = this.memberRepository.findByEmail(username);
        
        if (member.isPresent()) {  //Optional 에 SiteUser가 검색되면 (존재하면) 
        	
            return member.get();
            
        } else {
            throw new DataNotFoundException("siteuser not found - 해당 사용자는 존재하지 안습니다. ");
        }
    }

}