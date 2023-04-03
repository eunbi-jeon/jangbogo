package com.jangbogo.service.auth;

import com.jangbogo.advice.assertThat.DefaultAssert;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() ->
                        new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
                );

        return UserPrincipal.create(member);
    }

    @Transactional
    public UserDetails loadUserById(Long id) {
        Optional<Member> member = memberRepository.findById(id);
        DefaultAssert.isOptionalPresent(member);

        return UserPrincipal.create(member.get());
    }

}
