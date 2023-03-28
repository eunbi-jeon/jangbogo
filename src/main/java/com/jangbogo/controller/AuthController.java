package com.jangbogo.controller;

import com.jangbogo.dto.MemberRequestDto;
import com.jangbogo.dto.MemberResponseDto;
import com.jangbogo.dto.TokenDto;
import com.jangbogo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;

    @PostMapping("/create")
    public ResponseEntity<MemberResponseDto> signup(@RequestBody MemberRequestDto requestDto) {
        log.info("create 컨트롤러 실행");
        return ResponseEntity.ok(authService.signup(requestDto));
    }

    @GetMapping("/emailCheck")
    public int emailCheck(@RequestParam("email") String email) throws Exception {
        int result = authService.emailCheck(email);

        return result;
    }

    @GetMapping("/nameCheck")
    public int nickNameCheck(@RequestParam("nickname") String nickname) throws Exception {
        int result = authService.nameCheck(nickname);

        return result;
    }
    @PostMapping("/login")
    public TokenDto login(@RequestBody MemberRequestDto requestDto) {
        log.info("login 컨트롤러 실행");
        log.info("접속 아이디 = {}", requestDto.getEmail());
        log.info("접속 패스워드 = {}", requestDto.getPassword());
        return authService.login(requestDto);
    }
}
