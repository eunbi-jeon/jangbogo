package com.jangbogo.dto;

import com.jangbogo.constant.Role;
import com.jangbogo.domain.Member;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberRequestDto {

    //Request를 받을 때 쓰이는 dto

    private String email;
    private String password;
    private String nickname;
    private String region;
    private String age;

    public Member toMember(PasswordEncoder passwordEncoder) {
        return Member.builder()
                .email(email)
                .pass(passwordEncoder.encode(password))
                .nickName(nickname)
                .region(region)
                .age(age)
                .role(Role.ROLE_MEMBER)
                .build();
    }

    //UsernamePasswordAuthenticationToken를 반환하여 아이디와 비밀번호가 일치하는지 검증하는 로직을 넣을 수 있게함
    public UsernamePasswordAuthenticationToken toAuthentication() {
        return new UsernamePasswordAuthenticationToken(email, password);
    }
}
