package com.jangbogo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.jangbogo.domain.Member;
import com.jangbogo.dto.MessageDTO;
import com.jangbogo.message.response.Response;
import com.jangbogo.repository.MemberMSRepository;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.service.MessageService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class MessageController {

	private final MessageService messageService;
	private final MemberMSRepository  memberMSRepository;
	
	@ApiOperation(value = "쪽지 보내기" , notes = "쪽지 보내기")
	@ResponseStatus(HttpStatus.CREATED)
	@GetMapping("/messages")
	 public Response<?> sendMessage(@RequestBody MessageDTO messageDTO) {
		Member member = memberMSRepository.findByid(1).orElseThrow(() -> {
            return new IllegalArgumentException("유저를 찾을 수 없습니다.");
        });
		messageDTO.setSenderName(member.getNickName());
		
		return new Response<>("성공","쪽지를 보냈습니다.",messageService.write(messageDTO));
		
	}
	
	@ApiOperation(value = "받은 쪽지 읽기" , notes = "받은 쪽지 확인")
	@ResponseStatus(HttpStatus.OK)
	@GetMapping("/messages/received")
	public Response<?> getReceivedMessage(){
		Member member = MemberMSRepository.find
	}
	
}
