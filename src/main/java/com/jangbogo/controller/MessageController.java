package com.jangbogo.controller;

import javax.validation.Valid;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jangbogo.config.security.token.CurrentUser;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.exeption.MemberNotEqualsException;
import com.jangbogo.payload.request.message.MessageCreateRequest;
import com.jangbogo.payload.response.DM.Response;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.service.MessageService;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Tag(name = "Messages Controller", description = "Messages")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:3000")
public class MessageController {

    private final MessageService messageService;
    private final MemberRepository memberRepository;


    @GetMapping("/messages/findName/{searchValue}")
    public Response findReceiver(@PathVariable("searchValue") String nickname) {
    	List<Member> user = memberRepository.findByNameContaining(nickname);

        if(user == null) {
            new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.");
        }

        return Response.success(user);
    }


    @PostMapping("/messages")
    public Response createMessage(@Valid @RequestBody MessageCreateRequest req, @CurrentUser UserPrincipal userPrincipal) {

    	Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        return Response.success(messageService.createMessage(member, req));

    }

    @GetMapping("/messages/receiver")
    public Response receiveMessages(@CurrentUser UserPrincipal userPrincipal) {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        return Response.success(messageService.receiveMessages(member));
    }

    @GetMapping("/messages/receiver/{id}")
    public Response receiveMessage(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) throws MemberNotEqualsException {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        return Response.success(messageService.receiveMessage(id, member));
    }

    @GetMapping("/messages/sender")
    public Response sendMessages(@CurrentUser UserPrincipal userPrincipal) {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        return Response.success(messageService.sendMessages(member));
    }

    @GetMapping("/messages/sender/{id}")
    public Response sendMessage(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) throws MemberNotEqualsException {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        return Response.success(messageService.sendMessage(id, member));
    }


    @DeleteMapping("/messages/receiver/{id}")
    public Response deleteReceiveMessage(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) throws MemberNotEqualsException {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        messageService.deleteMessageByReceiver(id, member);
        return Response.success();
    }


    @DeleteMapping("/messages/sender/{id}")
    public Response deleteSendMessage(@PathVariable Long id, @CurrentUser UserPrincipal userPrincipal) throws MemberNotEqualsException {
        Member member = memberRepository.findById(userPrincipal.getId()).orElseThrow(() ->
                new UsernameNotFoundException("유저 정보를 찾을 수 없습니다.")
        );
        messageService.deleteMessageBySender(id, member);
        return Response.success();
    }

}