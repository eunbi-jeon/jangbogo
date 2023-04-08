package com.jangbogo.service.auth;

import com.jangbogo.advice.assertThat.DefaultAssert;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.domain.member.entity.Provider;
import com.jangbogo.domain.member.entity.Role;
import com.jangbogo.domain.member.entity.Token;
import com.jangbogo.domain.member.mapping.TokenMapping;
import com.jangbogo.payload.request.auth.*;
import com.jangbogo.payload.response.ApiResponse;
import com.jangbogo.payload.response.AuthResponse;
import com.jangbogo.payload.response.MailResponse;
import com.jangbogo.payload.response.Message;
import com.jangbogo.repository.auth.TokenRepository;
import com.jangbogo.repository.MemberRepository;

import com.jangbogo.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.ResponseEntity;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final CustomTokenProviderService customTokenProviderService;

    private final MemberRepository memberRepository;
    private final TokenRepository tokenRepository;

    private final FileService fileService;

    /* 회원 정보 조회 */
    public ResponseEntity<?> whoAmI(UserPrincipal userPrincipal){
        Optional<Member> member = memberRepository.findById(userPrincipal.getId());
        DefaultAssert.isOptionalPresent(member);
        ApiResponse apiResponse = ApiResponse.builder().check(true).information(member.get()).build();

        return ResponseEntity.ok(apiResponse);
    }

    /* 회원 탈퇴 */
    public ResponseEntity<?> delete(UserPrincipal userPrincipal){
        Optional<Member> user = memberRepository.findById(userPrincipal.getId());
        DefaultAssert.isTrue(user.isPresent(), "유저가 올바르지 않습니다.");

        Optional<Token> token = tokenRepository.findByUserEmail(user.get().getEmail());
        DefaultAssert.isTrue(token.isPresent(), "토큰이 유효하지 않습니다.");

        memberRepository.delete(user.get());
        tokenRepository.delete(token.get());

        ApiResponse apiResponse = ApiResponse.builder().check(true).information(Message.builder().message("회원 탈퇴하셨습니다.").build()).build();

        return ResponseEntity.ok(apiResponse);
    }

    /** 회원 정보 수정 **/
    public ResponseEntity<?> modifyMember(UserPrincipal userPrincipal, UpdateRequest updateRequest) {
        Member member = memberRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id="+userPrincipal.getId()));

        String password = passwordEncoder.encode(updateRequest.getPassword());
        member.updateMember(updateRequest.getName(), password, updateRequest.getRegion(), updateRequest.getAge());
        memberRepository.save(member);
        return ResponseEntity.ok(true);
    }

    /* 프로필 사진 변경 */
    public ResponseEntity<?> thumbnailModify(UserPrincipal userPrincipal, MultipartFile multipartFile) {
        Member member = memberRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id="+userPrincipal.getId()));

        try {
            if(member.getImageUrl() != null && !member.getImageUrl().isEmpty()){
                fileService.deleteFile(member.getImageUrl());
            }

            String oriImgName = multipartFile.getOriginalFilename();

            String imgName = fileService.uploadFile(oriImgName, multipartFile.getBytes());
            String uploadDir = "./img/profile/" + imgName;

            member.updateImageUrl(uploadDir);
            memberRepository.save(member);

            return ResponseEntity.ok(true);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

    /* 프로필 사진 삭제 */
    public ResponseEntity<?> thumbnailDelete(UserPrincipal userPrincipal) {
        Member member = memberRepository.findById(userPrincipal.getId())
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id="+userPrincipal.getId()));

        log.info("프로필 사진 삭제 서비스 처리 시작");

        try {
            log.info("프로필 사진 삭제 if문 진입");
            if(member.getImageUrl() != null && !member.getImageUrl().isEmpty()){
                fileService.deleteFile(member.getImageUrl());
            }

            log.info("프로필 사진 삭제 if문 탈출");
            member.deleteImageUrl();
            memberRepository.save(member);

            return ResponseEntity.ok(true);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }


    /** 로그인 **/
    public ResponseEntity<?> signin(SignInRequest signInRequest){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        signInRequest.getEmail(),
                        signInRequest.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        TokenMapping tokenMapping = customTokenProviderService.createToken(authentication);
        Token token = Token.builder()
                .refreshToken(tokenMapping.getRefreshToken())
                .userEmail(tokenMapping.getUserEmail())
                .build();
        tokenRepository.save(token);
        AuthResponse authResponse = AuthResponse.builder().accessToken(tokenMapping.getAccessToken()).refreshToken(token.getRefreshToken()).build();

        return ResponseEntity.ok(authResponse);
    }

    /** 회원가입 **/
    public ResponseEntity<?> signup(SignUpRequest signUpRequest){
        DefaultAssert.isTrue(!memberRepository.existsByEmail(signUpRequest.getEmail()), "해당 이메일이 존재하지 않습니다.");

        Member member = Member.builder()
                .name(signUpRequest.getName())
                .email(signUpRequest.getEmail())
                .password(passwordEncoder.encode(signUpRequest.getPassword()))
                .age(signUpRequest.getAge())
                .region(signUpRequest.getRegion())
                .provider(Provider.local)
                .role(Role.USER)
                .build();

        memberRepository.save(member);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/auth/")
                .buildAndExpand(member.getId()).toUri();
        ApiResponse apiResponse = ApiResponse.builder().check(true).information(Message.builder().message("회원가입에 성공하였습니다.").build()).build();

        return ResponseEntity.created(location).body(apiResponse);
    }

    public ResponseEntity<?> refresh(RefreshTokenRequest tokenRefreshRequest){
        //1차 검증
        boolean checkValid = valid(tokenRefreshRequest.getRefreshToken());
        DefaultAssert.isAuthentication(checkValid);

        Optional<Token> token = tokenRepository.findByRefreshToken(tokenRefreshRequest.getRefreshToken());
        Authentication authentication = customTokenProviderService.getAuthenticationByEmail(token.get().getUserEmail());

        //4. refresh token 정보 값을 업데이트 한다.
        //시간 유효성 확인
        TokenMapping tokenMapping;

        Long expirationTime = customTokenProviderService.getExpiration(tokenRefreshRequest.getRefreshToken());
        if(expirationTime > 0){
            tokenMapping = customTokenProviderService.refreshToken(authentication, token.get().getRefreshToken());
        }else{
            tokenMapping = customTokenProviderService.createToken(authentication);
        }

        Token updateToken = token.get().updateRefreshToken(tokenMapping.getRefreshToken());
        tokenRepository.save(updateToken);

        AuthResponse authResponse = AuthResponse.builder().accessToken(tokenMapping.getAccessToken()).refreshToken(updateToken.getRefreshToken()).build();

        return ResponseEntity.ok(authResponse);
    }


    /** 로그아웃 **/
    public ResponseEntity<?> signout(RefreshTokenRequest tokenRefreshRequest){
        boolean checkValid = valid(tokenRefreshRequest.getRefreshToken());
        DefaultAssert.isAuthentication(checkValid);

        //4 token 정보를 삭제한다.
        Optional<Token> token = tokenRepository.findByRefreshToken(tokenRefreshRequest.getRefreshToken());
        tokenRepository.delete(token.get());
        ApiResponse apiResponse = ApiResponse.builder().check(true).information(Message.builder().message("로그아웃 하였습니다.").build()).build();

        return ResponseEntity.ok(apiResponse);
    }

    private boolean valid(String refreshToken){

        //1. 토큰 형식 물리적 검증
        boolean validateCheck = customTokenProviderService.validateToken(refreshToken);
        DefaultAssert.isTrue(validateCheck, "Token 검증에 실패하였습니다.");

        //2. refresh token 값을 불러온다.
        Optional<Token> token = tokenRepository.findByRefreshToken(refreshToken);
        DefaultAssert.isTrue(token.isPresent(), "탈퇴 처리된 회원입니다.");

        //3. email 값을 통해 인증값을 불러온다
        Authentication authentication = customTokenProviderService.getAuthenticationByEmail(token.get().getUserEmail());
        DefaultAssert.isTrue(token.get().getUserEmail().equals(authentication.getName()), "사용자 인증에 실패하였습니다.");

        return true;
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

        if(memberRepository.existsByName(name)){
            return result = 1;
        }
        return result;
    }

    /* 임시비밀번호로 회원정보 업데이트 */
    public Member updatePassword(String email, String password){
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(()-> new IllegalArgumentException("해당 유저가 존재하지 않습니다. id="+email));

        String newPass = getNewPassword();

        member.updatePassWord(passwordEncoder.encode(password));
        memberRepository.save(member);

        return member;
    }

    /* 임시 비밀번호 생성 */
    public String getNewPassword(){
        char[] charSet = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

        String str = "";

        int idx = 0;
        for (int i = 0; i < 10; i++) {
            idx = (int) (charSet.length * Math.random());
            str += charSet[idx];
        }
        return str;
    }

    /* 메일 내용 생성 */
    public MailResponse createMailAndChangePassword(String email) {
        String newPass = getNewPassword();
        MailResponse mail = new MailResponse();
        mail.setAddress(email);
        mail.setTitle("[카트왕 장보고] 회원님의 임시 비밀번호 안내 이메일입니다.");
        mail.setMessage(newPass);

        updatePassword(email, newPass);

        return mail;
    }

    /* 회원가입 인증코드 작성 */
    public MailResponse sendCode(String email, String code) {
        MailResponse mail = new MailResponse();
        mail.setAddress(email);
        mail.setTitle("[카트왕 장보고] 이메일 인증 코드입니다.");
        mail.setMessage(code);

        return mail;
    }
}