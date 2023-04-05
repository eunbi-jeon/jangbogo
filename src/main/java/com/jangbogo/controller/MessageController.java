package com.jangbogo.controller;

import javax.validation.Valid;

import com.jangbogo.config.security.token.CurrentUser;
import com.jangbogo.config.security.token.UserPrincipal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.exeption.MemberNotEqualsException;
import com.jangbogo.payload.request.message.MessageCreateRequest;
import com.jangbogo.payload.response.DM.Response;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.service.MessageService;

import lombok.RequiredArgsConstructor;

@Tag(name = "Messages Controller", description = "Messages")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class MessageController {

    private final MessageService messageService;
    private final MemberRepository memberRepository;

    @Operation(summary = "쪽지 작성", description = "쪽지 보내기")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/messages")
    public Response createMessage(@Valid @RequestBody MessageCreateRequest req, @CurrentUser UserPrincipal userPrincipal) {    	
    	Member member = memberRepository.findByEmail(userPrincipal.getEmail()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
    	
        return Response.success(messageService.createMessage(member, req));
    }

    @Operation(summary = "받은 쪽지 전부 확인", description = "받은 쪽지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/receiver")
    public Response receiveMessages(@CurrentUser UserPrincipal userPrincipal) {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
    	System.out.println("사용자 정보 출력 ===> : " + userPrincipal.getEmail());
    	System.out.println("사용자 정보 출력 ===> : " + userPrincipal.getName());
    	System.out.println("사용자 정보 출력 ===> : " + userPrincipal.getUsername());
    	System.out.println("사용자 정보 출력 ===> : " + userPrincipal.getPassword());
        return Response.success(messageService.receiveMessages(member));
    }

    @Operation(summary = "받은 쪽지 중 한 개 확인", description = "받은 쪽지 중 하나를 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/receiver/{id}")
    public Response receiveMessage(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) throws MemberNotEqualsException {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        return Response.success(messageService.receiveMessage(id, member));
    }

    @Operation(summary = "보낸 쪽지 전부 확인", description = "보낸 쪽지함 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/sender")
    public Response sendMessages(@CurrentUser UserPrincipal userPrincipal) {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        return Response.success(messageService.sendMessages(member));
    }

    @Operation(summary = "보낸 쪽지 중 한 개 확인", description = "보낸 쪽지 중 하나를 확인")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/messages/sender/{id}")
    public Response sendMessage(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) throws MemberNotEqualsException {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        return Response.success(messageService.sendMessage(id, member));
    }

    @Operation(summary = "받은 쪽지 삭제", description = "받은 쪽지 삭제하기")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/receiver/{id}")
    public Response deleteReceiveMessage(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) throws MemberNotEqualsException {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        messageService.deleteMessageByReceiver(id, member);
        return Response.success();
    }

    @Operation(summary = "보낸 쪽지 삭제", description = "보낸 쪽지 삭제하기")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/messages/sender/{id}")
    public Response deleteSendMessage(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) throws MemberNotEqualsException {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        messageService.deleteMessageBySender(id, member);
        return Response.success();
    }

}