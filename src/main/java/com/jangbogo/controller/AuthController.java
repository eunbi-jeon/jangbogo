package com.jangbogo.controller;

import com.jangbogo.advice.payload.ErrorResponse;
import com.jangbogo.config.security.token.CurrentUser;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.exeption.MemberNotFoundException;
import com.jangbogo.payload.request.auth.*;
import com.jangbogo.payload.response.AuthResponse;
import com.jangbogo.payload.response.MailResponse;
import com.jangbogo.payload.response.Message;

import com.jangbogo.service.MailService;
import com.jangbogo.service.auth.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Authorization", description = "Authorization API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
@CrossOrigin("http://localhost:3000")
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    @GetMapping(value = "/")
    public ResponseEntity<?> whoAmI(@CurrentUser UserPrincipal userPrincipal) {
            return authService.whoAmI(userPrincipal);
    }

    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(@CurrentUser UserPrincipal userPrincipal){
        log.info("유저 정보 = {}", userPrincipal.getEmail());
        return authService.delete(userPrincipal);
    }

    /* 로그인 */
    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(@Valid @RequestBody SignInRequest signInRequest) {
        return authService.signin(signInRequest);
    }


    /* 회원가입 */
    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(@Valid @RequestBody SignUpRequest signUpRequest) {
        log.info("회원가입 실행");
        return authService.signup(signUpRequest);
    }

    /* 토큰 갱신 */
    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refresh(@Valid @RequestBody RefreshTokenRequest tokenRefreshRequest) {
        return authService.refresh(tokenRefreshRequest);
    }


    /* 로그아웃 */
    @PostMapping(value="/signout")
    public ResponseEntity<?> signout(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody RefreshTokenRequest tokenRefreshRequest) {
        return authService.signout(tokenRefreshRequest);
    }


    /* 회원 정보 수정 */
    @PatchMapping("/update")
    public ResponseEntity<?> update(@CurrentUser UserPrincipal userPrincipal, @Valid @RequestBody UpdateRequest updateRequest) {
        return authService.modifyMember(userPrincipal, updateRequest);
    }


    /* 프로필 이미지 변경 */
    @PostMapping("/thumbnail/update")
    public ResponseEntity<?> thumbnailUpdate(@CurrentUser UserPrincipal userPrincipal, @RequestPart(value="file", required=true) MultipartFile multipartFile
    ) {

        return authService.thumbnailModify(userPrincipal, multipartFile);
    }

    /* 프로필 이미지 삭제 */
    @PostMapping("/thumbnail/delete")
    public ResponseEntity<?> thumbnailDelete(@CurrentUser UserPrincipal userPrincipal) {
        return authService.thumbnailDelete(userPrincipal);
    }

    /* 닉네임 중복확인 */
    @GetMapping("/nameCheck")
    public int nameCheck(@RequestParam("name") String name) {
        int result = authService.nameCheck(name);

        return result;
    }

    /* 비밀번호 재설정 */
    @GetMapping("/find/password")
    public int updatePassword(@RequestParam("email") String email) {
        int result = emailCheck(email);
        if (result == 1) {
            MailResponse mail = authService.createMailAndChangePassword(email);
            mailService.sendMail(mail, "updatePass");
        }
        return result;
    }

    /* 이메일 인증 */
    @GetMapping("/emailCheck")
    public String mailConfirm(@RequestParam("email") String email) {

        int result = emailCheck(email);
        String code = authService.getNewPassword();

        log.info("결과값 = {}", result);
        if (result == 0) {
            MailResponse mail = authService.sendCode(email, code);
            mailService.sendMail(mail, "signUpCode");
        }else {
            return Integer.toString(result);
        }

        return code;
    }

    /* 이메일 중복확인 */
    public int emailCheck(@RequestParam("email") String email) {
        int result = authService.emailCheck(email);

        return result;
    }
}
