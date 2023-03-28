package com.jangbogo.domain.member.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jangbogo.domain.common.BaseTimeEntity;
import lombok.*;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@DynamicUpdate //실제 값이 변경된 컬럼으로만 update 쿼리 생성
@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false, unique = true)
    private String name;

    @JsonIgnore
    private String password;

    private String age;
    private String region; //지역정보

    private Integer report; //신고 받은 횟수

    private String imageUrl;

    @Column(nullable = false)
    private Boolean emailVerified = false;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Provider provider;

    private String providerId;

    @Enumerated(EnumType.STRING)
    private Role role;

    @Builder
    public Member(String email, String password, String name, Role role, Provider provider, String region, String age, String providerId, String imageUrl) {
        this.email = email;
        this.password = password;
        this.name = name;
        this.provider = provider;
        this.region = region;
        this.age = age;
        this.imageUrl = imageUrl;
        this.role = role;
    }

    //닉네임 변경
    public void updateName(String name){
        this.name = name;
    }

    //프로필 이미지
    public void updateImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

}
