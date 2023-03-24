//package com.jangbogo.controller;
//
//import com.jangbogo.dto.MemberDto;
//import com.jangbogo.service.MemberService;
//import com.jangbogo.service.MessageService;
//
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import javax.websocket.server.PathParam;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("/member")
//@Slf4j
//public class MemberController {
//
//	private final MemberService memberService;
//	private final MessageService messageService;
//
//	/* 회원가입 이메일 / 아이디 중복체크 */
//	@GetMapping("/check/nickname/{nickname}")
//	public ResponseEntity<?> checkNickname(@PathParam("nickname") String nickname) {
//		log.info("MemberController : 닉네임 중복확인 처리 시작");
//
//		if (memberService.checkNickname(nickname)) {
//			return ResponseEntity.status(HttpStatus.OK).body(false);
//		}
//
//		return ResponseEntity.status(HttpStatus.OK).body(true);
//	}
//
//	@GetMapping("/check/nickname/{email}")
//	public ResponseEntity<?> checkEmail(@PathParam("email") String email) {
//		log.info("MemberController : 닉네임 중복확인 처리 시작");
//
//		if (memberService.checkemail(email)) {
//			return ResponseEntity.status(HttpStatus.OK).body(false);
//		}
//
//		return ResponseEntity.status(HttpStatus.OK).body(true);
//	}
//
//	@PostMapping("/create")
//	public ResponseEntity<?> createMember(@ModelAttribute MemberDto memberDto) {
//		log.info("데이터 확인 email = {}", memberDto.getEmail());
//		memberService.saveMember(memberDto);
//		return ResponseEntity.status(HttpStatus.OK).body(true);
//	}
//
//}
