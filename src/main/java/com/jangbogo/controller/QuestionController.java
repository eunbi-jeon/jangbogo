package com.jangbogo.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.jangbogo.domain.Board.Question;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.AnswerDto;
import com.jangbogo.dto.QuestionDto;
import com.jangbogo.service.MemberService;
import com.jangbogo.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
public class QuestionController {
	
	private final QuestionService questionService; 
	private final MemberService memberService;		
	
	@GetMapping("/board/list")
	public Page<Question> questionList(@RequestParam (value="page", defaultValue="0") int page, 
		@RequestParam(value = "kw", defaultValue = "") String kw) {
		
		System.out.println("Question 컨트롤러 잘 호출됨 ");
		System.out.println("page ===> : " + page);
		System.out.println("kw  ===> : " + kw);
		System.out.println("==========================");
	
	//Page<Question> paging = this.questionService.getList(page, kw); 
		Page<Question> paging = this.questionService.getList(page); 
	System.out.println("서비스 잘 작동됨 : ");
	
	
//	Page<Question> paging = this.questionService.getList(pageable, spec); 

	return paging;
//	model.addAttribute("paging", paging); 
//	model.addAttribute("kw", kw);
			
//	return "question_list"; 
	}
	
	
	@GetMapping(value = "/board/detail/{id}")
	public String questionDetail (@PathVariable("id") Long id , AnswerDto answerDto) {
	Question q = 
			this.questionService.getQuestion(id); 
//	q.setReadCount(q.getReadCount() + 1);
//	model.addAttribute("question", q); 
	
	return "question_detail"; 
	}

	@GetMapping("/board/create")
	public String questionCreate(QuestionDto questionDto) {
	return "question_form"; 
	}

	@PostMapping("/board/create")
	public String questionCreate(			

		@Valid QuestionDto questionDto, BindingResult bindingResult, Principal principal)
		 {
			
			if (bindingResult.hasErrors()) { 
				return "question_form"; 
			}
	
	Member member = this.memberService.getMember(principal.getName());
	
	this.questionService.create(questionDto.getBoard(),questionDto.getSubject(), questionDto.getContent(),member); 			
	
	return "redirect:/question/list";      
	}

	@GetMapping("/board/modify/{id}")
	public String questionModify(QuestionDto questionDto, @PathVariable("id") Long id, Principal principal) {
	
	Question question = this.questionService.getQuestion(id);
	
	if(!question.getName().getEmail().equals(principal.getName())) {
		
	    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
	    
	}
	
	questionDto.setSubject(question.getSubject());
	questionDto.setContent(question.getContent());
	
	
	return "question_form";
	}
	
	@PostMapping("/board/modify/{id}")
	public String questionModify(@Valid QuestionDto questionDto, BindingResult bindingResult, 
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

	@GetMapping("/board/vote/{id}")
	public String questionVote(Principal principal, @PathVariable("id") Long id) {
	Question question = this.questionService.getQuestion(id);
	Member member = this.memberService.getMember(principal.getName());
	this.questionService.vote(question, member);
	return String.format("redirect:/question/detail/%s", id);
	}
	
	@GetMapping("/board/report/{id}")
	public String questionReport(Principal principal, @PathVariable("id") Long id) {
	Question question = this.questionService.getQuestion(id);
	Member member = this.memberService.getMember(principal.getName());
	this.questionService.report(question, member);
	return String.format("redirect:/question/detail/%s", id);
	}

}
