package com.jangbogo.service.auth;

import com.jangbogo.config.security.auth.Oauth2UserInfo;
import com.jangbogo.advice.assertThat.DefaultAssert;
import com.jangbogo.config.security.auth.Oauth2UserInfoFactory;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.domain.member.entity.Provider;
import com.jangbogo.domain.member.entity.Role;
import com.jangbogo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomDefaultOAuth2UserService extends DefaultOAuth2UserService {

    private final MemberRepository memberRepository;

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);
        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (Exception e) {
            DefaultAssert.isAuthentication(e.getMessage());
        }
        return null;
    }

    private OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        Oauth2UserInfo oAuth2UserInfo = Oauth2UserInfoFactory.getOAuth2UserInfo(oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());
        DefaultAssert.isAuthentication(!oAuth2UserInfo.getEmail().isEmpty());

        Optional<Member> memberOptional = memberRepository.findByEmail(oAuth2UserInfo.getEmail());
        Member member;
        if(memberOptional.isPresent()) {
            member = memberOptional.get();
            DefaultAssert.isAuthentication(member.getProvider().equals(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId())));
            member = updateExistingUser(member, oAuth2UserInfo);
        } else {
            member = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
        }

        return UserPrincipal.create(member, oAuth2User.getAttributes());
    }

    private Member registerNewUser(OAuth2UserRequest oAuth2UserRequest, Oauth2UserInfo oAuth2UserInfo) {
        Member member = Member.builder()
                .provider(Provider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))
                .providerId(oAuth2UserInfo.getId())
                .name(oAuth2UserInfo.getName())
                .email(oAuth2UserInfo.getEmail())
//                .region(oAuth2UserInfo.getRegion())
//                .age(oAuth2UserInfo.getAge())
                .imageUrl(oAuth2UserInfo.getImageUrl())
                .role(Role.USER)
                .build();

        return memberRepository.save(member);
    }

    private Member updateExistingUser(Member member, Oauth2UserInfo oAuth2UserInfo) {

        member.updateName(oAuth2UserInfo.getName());
        member.updateImageUrl(oAuth2UserInfo.getImageUrl());

        return memberRepository.save(member);
    }
}
