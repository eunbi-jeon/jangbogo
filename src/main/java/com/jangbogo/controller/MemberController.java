package com.jangbogo.controller;

import com.jangbogo.advice.payload.ErrorResponse;
import com.jangbogo.config.security.token.CurrentUser;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.payload.request.auth.SignUpRequest;
import com.jangbogo.payload.response.Message;
import com.jangbogo.service.MemberService;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@Slf4j
@Tag(name = "Authorization", description = "Authorization API")
@RequiredArgsConstructor
@RestController
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "유저 정보 갱신", description = "현제 접속된 유저의 정보를 수정 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "유저 정보 갱신 성공",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = Message.class) ) } ),
            @ApiResponse(responseCode = "400", description = "유저 정보 갱신 실패", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = ErrorResponse.class) ) } ),
    })
    @PostMapping("/update")
    public ResponseEntity<?> update(
            @Parameter(description = "Accesstoken을 입력해주세요.", required = true) @CurrentUser UserPrincipal userPrincipal,
            @Parameter(description = "Schemas의 SignUpRequest를 참고해주세요.", required = true) @Valid @RequestBody SignUpRequest signUpRequest
    ) {

        log.info("회원 정보 수정 컨트롤러 처리");
        return memberService.modifyMember(userPrincipal, signUpRequest);
    }


}
