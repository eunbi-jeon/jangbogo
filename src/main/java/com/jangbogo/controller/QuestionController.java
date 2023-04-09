package com.jangbogo.controller;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.Optional;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

	//내가쓴글 조회
	@GetMapping("/board/my")
	public ResponseEntity<List<Question>> myBoardList(@CurrentUser UserPrincipal userPrincipal){
		return questionService.getMyBoard(userPrincipal);
	}

	@GetMapping("/board/list/{board_id}")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public ResponseEntity<Page<Question>> questionList(
			@RequestParam(value = "region", required = false) String region,
			@PathVariable("board_id") Long board_id,
			@RequestParam(value = "page", defaultValue = "0") int page,
			@RequestParam(value = "kw", defaultValue = "") String kw) {

		Page<Question> paging = this.questionService.getList(board_id,region,page);

		if (paging.getContent().isEmpty()) {
			return ResponseEntity.noContent().build(); // 비어있는 경우 null 반환
		}

		return ResponseEntity.ok(paging); // 비어있지 않은 경우 Page 객체 반환
	}

	@PostMapping("/board/create/{board_id}")
	public void questionCreate(@PathVariable("board_id") Long board_id, @RequestBody QuestionDto questionDto, BindingResult bindingResult, @CurrentUser UserPrincipal userPrincipal) {
		// @RequestBody 어노테이션을 추가하여 Request Body에서 데이터를 읽어옴
		System.out.println("create controller 호출");
		if (bindingResult.hasErrors()) {
			System.out.println("create controller 호출");

		}

		Member member = this.memberService.getMember(userPrincipal.getEmail());

		this.questionService.create(board_id, member.getRegion(), questionDto.getSubject(), questionDto.getContent(), member);

	}
	
	
	@GetMapping("/board/detail/{id}")
	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
	public ResponseEntity<Map<String, Object>> questionDetail (@PathVariable("id") Long id , AnswerDto answerDto, @CurrentUser UserPrincipal userPrincipal) {
	    Question q = this.questionService.getQuestion(id); 
	    Optional<Member> member = memberRepository.findByEmail(userPrincipal.getEmail());

	    if (q.getContent().isEmpty()) {
	        return ResponseEntity.noContent().build(); // 비어있는 경우 null 반환
	    }

	    Map<String, Object> response = new HashMap<>();
	    response.put("question", q);
	    response.put("member", member.orElse(null));
	    return ResponseEntity.ok(response);
	}
		
	@PutMapping("/board/modify/{id}")
	public String questionModify(@RequestBody QuestionDto questionDto, BindingResult bindingResult,
								 @CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id) {
	
	
	if (bindingResult.hasErrors()) {
	    return "question_form";
	}
	
	Question question = this.questionService.getQuestion(id);
	
	
	if (!question.getName().getEmail().equals(userPrincipal.getEmail())) {
	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	}
	
	this.questionService.modify(question, questionDto.getSubject(), questionDto.getContent());
	
	return String.format("redirect:/question/detail/%s", id);
	}
	
	
	
	@GetMapping("/board/delete/{id}")
	public String questionDelete(@CurrentUser UserPrincipal userPrincipall, @PathVariable("id") Long id) {
	
	Question question = this.questionService.getQuestion(id);
	
	if (!question.getName().getEmail().equals(userPrincipall.getEmail())) {
		
	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
	    
	}
	
	this.questionService.delete(question);
	
	return "redirect:/";
	}
	
	@GetMapping("/board/{id}")
	public void getQuestion(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id) {
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
	public String questionVote(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id) {
		System.out.println("vote 컨트롤러 호출");
	Question question = this.questionService.getQuestion(id);
	Member member = this.memberService.getMember(userPrincipal.getEmail());
	this.questionService.vote(question, member);
	return String.format("redirect:/booard/detail/%s", id);
	}
	
	@GetMapping("/board/report/{id}")
	public String questionReport(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id) {
	Question question = this.questionService.getQuestion(id);
	Member member = this.memberService.getMember(userPrincipal.getEmail());
	this.questionService.report(question, member);
	return String.format("redirect:/question/detail/%s", id);
	}
	

}
