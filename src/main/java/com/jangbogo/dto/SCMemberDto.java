package com.jangbogo.dto;

import javax.persistence.Column;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SCMemberDto {

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false, unique = true)
    private String nickName;

    @Column(nullable = false)
    private String pass;

<<<<<<< HEAD
    private String loc; //지역정보
=======

>>>>>>> ed6d9544c8bb75292349dd76fd507a0d1827cbaf

}
