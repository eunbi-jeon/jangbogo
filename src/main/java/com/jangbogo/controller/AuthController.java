package com.jangbogo.controller;

import com.jangbogo.dto.MemberRequestDto;
import com.jangbogo.dto.MemberResponseDto;
import com.jangbogo.dto.TokenDto;
import com.jangbogo.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.repository.query.Param;
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
    public ResponseEntity<TokenDto> login(@RequestBody MemberRequestDto requestDto) {
        return ResponseEntity.ok(authService.login(requestDto));
    }
}
