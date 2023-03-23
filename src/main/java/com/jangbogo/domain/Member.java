package com.jangbogo.domain;

import com.jangbogo.constant.Role;
import com.jangbogo.domain.common.BaseTimeEntity;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String pass;

    private String age;
    private String loc; //지역정보

    private Integer report; //신고 받은 횟수

    private String provider; //소셜 로그인
    private String refreshToken; //JWT
    private Boolean emailAuth; //이메일 인증 여부

    private Role role;

    @Builder
    public Member(String email, String nickName, String pass, String loc, Boolean emailAuth) {
        this.email = email;
        this.nickName = nickName;
        this.pass = pass;
        this.loc = loc;
        this.role = Role.MEMBER;
        this.emailAuth = emailAuth;
    }


    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    //이메일 인증 처리
    public void emailVerifiedSuccess() {
        this.emailAuth = true;
    }
}
