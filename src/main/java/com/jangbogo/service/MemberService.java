package com.jangbogo.service;

import com.jangbogo.advice.assertThat.DefaultAssert;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.payload.request.auth.SignUpRequest;
import com.jangbogo.payload.response.ApiResponse;
import com.jangbogo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    public ResponseEntity<?> readByUser(UserPrincipal userPrincipal){
        Optional<Member> user = memberRepository.findById(userPrincipal.getId());
        DefaultAssert.isOptionalPresent(user);
        ApiResponse apiResponse = ApiResponse.builder().check(true).information(user.get()).build();
        return ResponseEntity.ok(apiResponse);
    }

    /** 회원 정보 수정 **/
    public ResponseEntity<?> modifyMember(UserPrincipal userPrincipal, SignUpRequest signUpRequest) {

        log.info("회원 정보 수정 서비스 처리");
        Member member = memberRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id="+userPrincipal.getId()));

        String password = passwordEncoder.encode(signUpRequest.getPassword());
        member.updateMember(signUpRequest.getName(), password, signUpRequest.getRegion(), signUpRequest.getAge());

        log.info("회원 정보 수정 완료");
        return ResponseEntity.ok(true);
    }
}