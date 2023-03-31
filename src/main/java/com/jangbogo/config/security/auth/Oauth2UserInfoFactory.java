package com.jangbogo.config.security.auth;

import com.jangbogo.advice.assertThat.DefaultAssert;
import com.jangbogo.config.security.auth.company.Kakao;
import com.jangbogo.config.security.auth.company.Naver;
import com.jangbogo.domain.member.entity.Provider;

import java.util.Map;

public class Oauth2UserInfoFactory {
    public static Oauth2UserInfo getOAuth2UserInfo(String registrationId, Map<String, Object> attributes) {
        if (registrationId.equalsIgnoreCase(Provider.naver.toString())) {
            return new Naver(attributes);
        } else if (registrationId.equalsIgnoreCase(Provider.kakao.toString())) {
            return new Kakao(attributes);
        } else {
            DefaultAssert.isAuthentication("해당 oauth2 기능은 지원하지 않습니다.");
        }
        return null;
    }
}
