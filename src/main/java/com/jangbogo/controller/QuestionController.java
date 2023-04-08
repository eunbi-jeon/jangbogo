package com.jangbogo.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.jangbogo.config.security.token.CurrentUser;
import com.jangbogo.config.security.token.UserPrincipal;

import com.jangbogo.domain.Board.Board;
import com.jangbogo.exeption.DataNotFoundException;
import com.jangbogo.repository.BoardRepository;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.annotation.*;

import org.springframework.web.server.ResponseStatusException;

import com.jangbogo.config.security.token.CurrentUser;
import com.jangbogo.config.security.token.UserPrincipal;
import com.jangbogo.domain.Board.Question;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.AnswerDto;
import com.jangbogo.dto.QuestionDto;
import com.jangbogo.repository.MemberRepository;
import com.jangbogo.service.MemberService;
import com.jangbogo.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:3000")
public class QuestionController {
	
	private final QuestionService questionService; 
	private final MemberService memberService;
	private final MemberRepository memberRepository;
	private final BoardRepository boardRepository;
  
	//내가쓴글 조회
	@GetMapping("/board/my")
	public ResponseEntity<List<Question>> myBoardList(@CurrentUser UserPrincipal userPrincipal){
		log.info("퀘스쳔 컨트롤러 회원정보 = {}", userPrincipal.getEmail());
		return questionService.getMyBoard(userPrincipal);
	}

	@GetMapping("/board/list/{board_id}")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public ResponseEntity<Page<Question>> questionList(
			@RequestParam(value = "region", required = false) String region,
			@PathVariable("board_id") Long board_id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) {
		System.out.println("list컨트롤러 잘 호출됌~~~");
		System.out.println("board_id:"+board_id);
		System.out.println("region:"+region);
		System.out.println("page:"+page);

		Page<Question> paging = this.questionService.getList(board_id,region,page);

		System.out.println("list 컨트롤러~~~~~~~~~");
		if (paging.getContent().isEmpty()) {
			return ResponseEntity.noContent().build(); // 비어있는 경우 null 반환
		}

		return ResponseEntity.ok(paging); // 비어있지 않은 경우 Page 객체 반환
	}

	@PostMapping("/board/create/{board_id}")
	public void questionCreate(@PathVariable("board_id") Long board_id,@RequestBody QuestionDto questionDto, BindingResult bindingResult, Principal principal) {
		// @RequestBody 어노테이션을 추가하여 Request Body에서 데이터를 읽어옴
		System.out.println("create controller 호출");
		if (bindingResult.hasErrors()) {
			System.out.println("create controller 호출");

		}

		System.out.println("create controller 호출");
		System.out.println("Board타입: "+board_id);
		System.out.println("region확이니ㅣㅣㅣㅣㅣㅣ:"+questionDto.getRegion());

		Member member = this.memberService.getMember(principal.getName());

		this.questionService.create(board_id, questionDto.getRegion(),questionDto.getSubject(), questionDto.getContent(), member);

	}
	
	
	@GetMapping("/board/detail/{id}")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public ResponseEntity<Map<String, Object>> questionDetail (@PathVariable("id") Long id , AnswerDto answerDto, @CurrentUser UserPrincipal userPrincipal) {
	    System.out.println("아이디======"+id);
	    System.out.println("접속한 유저정보 : "+userPrincipal.getEmail());
	    Question q = this.questionService.getQuestion(id); 
	    Optional<Member> member = memberRepository.findByEmail(userPrincipal.getEmail());
	    System.out.println("컨트롤러 호출이 됨");

	    if (q.getContent().isEmpty()) {
	        return ResponseEntity.noContent().build(); // 비어있는 경우 null 반환
	    }

	    System.out.println(q.getContent());
	    Map<String, Object> response = new HashMap<>();
	    response.put("question", q);
	    response.put("member", member.orElse(null));
	    return ResponseEntity.ok(response);
	}
		
	@PutMapping("/board/modify/{id}")
	public String questionModify(@RequestBody QuestionDto questionDto, BindingResult bindingResult, 
	    Principal principal, @PathVariable("id") Long id) {
	
	
	if (bindingResult.hasErrors()) {
	    return "question_form";
	}
	
	Question question = this.questionService.getQuestion(id);
	
	
	if (!question.getName().getEmail().equals(principal.getName())) {
	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	}
	
	this.questionService.modify(question, questionDto.getSubject(), questionDto.getContent());
	
	return String.format("redirect:/question/detail/%s", id);
	}
	
	
	
	@GetMapping("/board/delete/{id}")
	public String questionDelete(Principal principal, @PathVariable("id") Long id) {
	
	Question question = this.questionService.getQuestion(id);
	
	if (!question.getName().getEmail().equals(principal.getName())) {
		
	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
	    
	}
	
	this.questionService.delete(question);
	
	return "redirect:/";
	}
	
	@GetMapping("/board/{id}")
	public void getQuestion(Principal principal, @PathVariable("id") Long id) {
		Question question = this.questionService.getQuestion(id);
		System.out.println("question 호출 겟");
		question.setSubject(question.getSubject());
		question.setContent(question.getContent());
		this.questionService.save(question);
	}
	

	// 조회수
	@PostMapping("/board/increment-read-count/{id}")
	public ResponseEntity<Question> incrementReadCount(@PathVariable Long id) {
		Question question = questionService.findById(id);
	    question.setReadCount(question.getReadCount() + 1);
	    Question updatedQuestion = questionService.save(question);
	    System.out.println("조회수~~~~~~~~~~~~~~~~");
	    return ResponseEntity.ok(updatedQuestion);
	}
	
	@PutMapping("/board/{id}/vote")
	public String questionVote(Principal principal, @PathVariable("id") Long id) {
		System.out.println("vote 컨트롤러 호출");
	Question question = this.questionService.getQuestion(id);
	Member member = this.memberService.getMember(principal.getName());
	this.questionService.vote(question, member);
	return String.format("redirect:/booard/detail/%s", id);
	}
	
	@GetMapping("/board/report/{id}")
	public String questionReport(Principal principal, @PathVariable("id") Long id) {
	Question question = this.questionService.getQuestion(id);
	Member member = this.memberService.getMember(principal.getName());
	this.questionService.report(question, member);
	return String.format("redirect:/question/detail/%s", id);
	}
	

}
