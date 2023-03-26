package com.jangbogo.service;

import com.jangbogo.domain.Member;
import com.jangbogo.dto.MemberRequestDto;
import com.jangbogo.dto.MemberResponseDto;
import com.jangbogo.dto.TokenDto;
import com.jangbogo.jwt.TokenProvider;
import com.jangbogo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final AuthenticationManagerBuilder managerBuilder;
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    public MemberResponseDto signup(MemberRequestDto requestDto) {

        Member member = requestDto.toMember(passwordEncoder);
        return MemberResponseDto.of(memberRepository.save(member));
    }

    //이메일 중복확인
    public int emailCheck(String email) {
        int result = 0;

        if(memberRepository.existsByEmail(email)){
            return result = 1;
        }
        return result;
    }

    //닉네임 중복확인
    public int nameCheck(String name) {
        int result = 0;

        if(memberRepository.existsByNickName(name)){
            return result = 1;
        }
        return result;
    }

    public TokenDto login(MemberRequestDto requestDto) {
        UsernamePasswordAuthenticationToken authenticationToken = requestDto.toAuthentication();

        Authentication authentication = managerBuilder.getObject().authenticate(authenticationToken);

        return tokenProvider.generateTokenDto(authentication);
    }

}
