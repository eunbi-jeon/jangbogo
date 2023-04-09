package com.jangbogo.controller;

import java.security.Principal;
import java.util.List;

import javax.validation.Valid;

import com.jangbogo.config.security.token.CurrentUser;
import com.jangbogo.config.security.token.UserPrincipal;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.jangbogo.domain.Board.Answer;
import com.jangbogo.domain.Board.Board;
import com.jangbogo.domain.Board.Question;
import com.jangbogo.domain.member.entity.Member;
import com.jangbogo.dto.AnswerDto;
import com.jangbogo.service.AnswerService;
import com.jangbogo.service.MemberService;
import com.jangbogo.service.QuestionService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@RestController
@CrossOrigin("http://localhost:3000")
public class AnswerController {
	private final QuestionService questionService;
	private final AnswerService answerService; 
	private final MemberService memberService;

    //내가 쓴 댓글 조회
    @GetMapping("/answer/my")
    public ResponseEntity<List<Answer>> myBoardList(@CurrentUser UserPrincipal userPrincipal){
        return answerService.getMyAnswer(userPrincipal);
    }

	@PostMapping("/board/answer/create/{id}")
	public void answerCreate(@RequestBody AnswerDto answerDto,@PathVariable("id") Long id ,BindingResult bindingResult, @CurrentUser UserPrincipal userPrincipal) {

	    Question question = this.questionService.getQuestion(id);
	    Member member = this.memberService.getMember(userPrincipal.getEmail());

        try {

            if (answerDto.getParentId() == null) { // 댓글 생성
                this.answerService.create(question, answerDto.getContent(), member);
            } else { // 대댓글 생성
                this.answerService.createChildReply(question, answerDto.getParentId().getId(), answerDto.getContent(), member);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}
    
    //답변 수정
    @PutMapping("/answer/modify/{id}")
    public String answerModify(@RequestBody AnswerDto answerDto, BindingResult bindingResult,
            @PathVariable("id") Long id, @CurrentUser UserPrincipal userPrincipal) {
    	
        if (bindingResult.hasErrors()) {
            return "answer_form";
        }
        Answer answer = this.answerService.getAnswer(id);
        if (!answer.getName().getEmail().equals(userPrincipal.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.answerService.modify(answer, answerDto.getContent());

        return String.format("redirect:/question/detail/%s#answer_%s", 
                answer.getQuestion().getId(), answer.getId());
    }
    
    //답변 삭제 
    @GetMapping("/answer/delete/{id}")
    public String answerDelete(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id) {
    	
        Answer answer = this.answerService.getAnswer(id);
        
        if (!answer.getName().getEmail().equals(userPrincipal.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        
        System.out.println("삭제 컨트롤러 호출");
        this.answerService.delete(answer);
        
        return String.format("redirect:/question/detail/%s", answer.getQuestion().getId());
    }
    
    @GetMapping("/answer/vote/{id}")
    public String answerVote(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id) {
        Answer answer = this.answerService.getAnswer(id);
        Member member = this.memberService.getMember(userPrincipal.getEmail());
        this.answerService.vote(answer, member);

        return String.format("redirect:/question/detail/%s#answer_%s", 
                answer.getQuestion().getId(), answer.getId());
    }
    
    @GetMapping("/answer/report/{id}")
    public String answerReport(@CurrentUser UserPrincipal userPrincipal, @PathVariable("id") Long id) {

            Answer answer = this.answerService.getAnswer(id);
            Member member = this.memberService.getMember(userPrincipal.getEmail());
            this.answerService.report(answer, member);
        return String.format("redirect:/question/detail/%s#answer_%s", 
                answer.getQuestion().getId(), answer.getId());
    }
    
//	@PostMapping("/board/answer/list/{id}")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	public ResponseEntity<Page<Answer>> answerList(
//			@RequestParam(value = "page", defaultValue = "0") int page,
//			@RequestParam(value = "kw", defaultValue = "") String kw) {
//
//		Page<Answer> paging = this.answerService.getList(page);
//		System.out.println("answerlist 호출");
//
//		if (paging.getContent().isEmpty()) {
//			return ResponseEntity.noContent().build(); // 비어있는 경우 null 반환
//		}
//
//		return ResponseEntity.ok(paging); // 비어있지 않은 경우 Page 객체 반환
//	}
    
    
    
//	@GetMapping("/answer/list/{id}")
//	@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//	public ResponseEntity<Page<Answer>> answerList(
//			@RequestParam(value = "page", defaultValue = "0") int page,
//			@RequestParam(value = "kw", defaultValue = "") String kw) {
//
//		Page<Answer> paging = this.answerService.getList(page);
//
//
//		if (paging.getContent().isEmpty()) {
//			return ResponseEntity.noContent().build(); // 비어있는 경우 null 반환
//		}
//
//		return ResponseEntity.ok(paging); // 비어있지 않은 경우 Page 객체 반환
//	}

}
