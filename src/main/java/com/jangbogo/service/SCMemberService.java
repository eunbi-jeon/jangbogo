package com.jangbogo.service;

import org.springframework.stereotype.Service;

import com.jangbogo.domain.Member;

import com.jangbogo.repository.SCMemberRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class SCMemberService {

    private final SCMemberRepository memberRepository;

    
    public Member getMemberId(Long id) {
    
    	return memberRepository.findById(id).get();
    }
}
