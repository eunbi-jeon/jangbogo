package com.jangbogo.service;

import org.springframework.stereotype.Service;

import com.jangbogo.domain.SCMember;

import com.jangbogo.repository.SCMemberRepository;

import lombok.RequiredArgsConstructor;


@Service
@RequiredArgsConstructor

public class SCMemberService {

    private final SCMemberRepository memberRepository;

    
    public SCMember getMemberId(Long id) {
    
    	return memberRepository.findById(id).get();
    }
}
