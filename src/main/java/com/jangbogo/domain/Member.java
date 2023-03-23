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

    @Enumerated(EnumType.STRING)
    private Role role;


    /** 닉네임, 비밀번호 수정에 사용 **/
    public void setNickname(String nickName) {
        this.nickName = nickName;
    }

    public void setPassword(String pass) {
        this.pass = pass;
    }

    @Builder
    public Member(String email, String nickName, String pass, String loc, Role role) {
        this.email = email;
        this.nickName = nickName;
        this.pass = pass;
        this.loc = loc;
        this.role = role;
    }

}
