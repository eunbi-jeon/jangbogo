package com.jangbogo.controller;

import com.jangbogo.advice.payload.ErrorResponse;
import com.jangbogo.config.security.token.CurrentUser;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.payload.request.auth.*;
import com.jangbogo.payload.response.AuthResponse;
import com.jangbogo.payload.response.MailResponse;
import com.jangbogo.payload.response.Message;
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
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Authorization", description = "Authorization API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;
    private final MailService mailService;

    @Operation(summary = "유저 정보 확인", description = "현제 접속된 유저정보를 확인합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 확인 성공",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Member.class) ) } ),
            @ApiResponse(responseCode = "400", description = "유저 확인 실패",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @GetMapping(value = "/")
    public ResponseEntity<?> whoAmI(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
        return authService.whoAmI(userPrincipal);
    }

    @Operation(summary = "유저 정보 삭제", description = "현제 접속된 유저정보를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 삭제 성공",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "유저 삭제 실패",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @DeleteMapping(value = "/delete")
    public ResponseEntity<?> delete(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ){
        return authService.delete(userPrincipal);
    }
    /* 로그인 */
    @Operation(summary = "유저 로그인", description = "유저 로그인을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 로그인 성공",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = AuthResponse.class) ) } ),
            @ApiResponse(responseCode = "400", description = "유저 로그인 실패",
                    content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value = "/signin")
    public ResponseEntity<?> signin(
            @Parameter(description = "Schemas의 SignInRequest를 참고해주세요.", required = true) @Valid @RequestBody SignInRequest signInRequest
    ) {
        return authService.signin(signInRequest);
    }


    /* 회원가입 */
    @Operation(summary = "유저 회원가입", description = "유저 회원가입을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "회원가입 성공",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "회원가입 실패",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value = "/signup")
    public ResponseEntity<?> signup(
            @Parameter(description = "Schemas의 SignUpRequest를 참고해주세요.", required = true) @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        log.info("회원가입 실행");
        return authService.signup(signUpRequest);
    }

    @Operation(summary = "토큰 갱신", description = "신규 토큰 갱신을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "토큰 갱신 성공",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AuthResponse.class) ) } ),
            @ApiResponse(responseCode = "400", description = "토큰 갱신 실패", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value = "/refresh")
    public ResponseEntity<?> refresh(
            @Parameter(description = "Schemas의 RefreshTokenRequest를 참고해주세요.", required = true) @Valid @RequestBody RefreshTokenRequest tokenRefreshRequest
    ) {
        return authService.refresh(tokenRefreshRequest);
    }


    /* 로그아웃 */
    @Operation(summary = "유저 로그아웃", description = "유저 로그아웃을 수행합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "로그아웃 성공",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "로그아웃 실패",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping(value="/signout")
    public ResponseEntity<?> signout(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "Schemas의 RefreshTokenRequest를 참고해주세요.", required = true) @Valid @RequestBody RefreshTokenRequest tokenRefreshRequest
    ) {
        return authService.signout(tokenRefreshRequest);
    }

    @Operation(summary = "유저 정보 갱신", description = "현제 접속된 유저의 정보를 수정 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 갱신 성공",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "유저 정보 갱신 실패", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PatchMapping("/update")
    public ResponseEntity<?> update(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "Schemas의 SignUpRequest를 참고해주세요.", required = true) @Valid @RequestBody UpdateRequest updateRequest
    ) {
        return authService.modifyMember(userPrincipal, updateRequest);
    }


    /* 프로필 이미지 변경 */
    @Operation(summary = "유저 프로필 이미지 변경", description = "현제 접속된 유저의 프로필 사진을 수정 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 갱신 성공",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "유저 정보 갱신 실패", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/thumbnail/update")
    public ResponseEntity<?> thumbnailUpdate(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @RequestPart(value="file", required=true) MultipartFile multipartFile
    ) {

        return authService.thumbnailModify(userPrincipal, multipartFile);
    }

    /* 프로필 이미지 삭제 */
    @Operation(summary = "유저 프로필 이미지 삭제", description = "현제 접속된 유저의 프로필 사진을 삭제 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 갱신 성공",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "유저 정보 갱신 실패", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/thumbnail/delete")
    public ResponseEntity<?> thumbnailDelete(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal
    ) {
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
