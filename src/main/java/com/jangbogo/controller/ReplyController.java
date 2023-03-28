package com.jangbogo.controller;

import java.security.Principal;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import com.jangbogo.controller.generic.GenericAnswerController;
import com.jangbogo.domain.Answer;
import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.Reply;
import com.jangbogo.dto.ReplyDto;
import com.jangbogo.repository.ReplyRepository;
import com.jangbogo.service.AnswerService;
import com.jangbogo.service.BoardService;
import com.jangbogo.service.MemberService;
import com.jangbogo.service.ReplyService;
import com.jangbogo.service.generic.GenericAnswerService;

import lombok.RequiredArgsConstructor;

@Controller
public class ReplyController extends GenericAnswerController {
	
	private final ReplyService replyService;
	private final BoardService boardService;
	private final MemberService memberService;
	private final ReplyRepository replyRepository;
	
    public ReplyController(ReplyService replyService,
            BoardService boardService,
            MemberService memberService,
            ReplyRepository replyRepository,
            GenericAnswerService<Reply> genericReplyService) {
				super(genericReplyService);
				this.replyService = replyService;
				this.boardService = boardService;
				this.memberService = memberService;
				this.replyRepository = replyRepository;
    }
	// 댓글 달기
	@PostMapping("/reply/create/{id}")
 	public String createReply(Model model, @PathVariable("id") Long id, @Valid
 			ReplyDto replyDto, BindingResult bindingResult, Principal principal,
 			@RequestParam(value = "parentId") Long parentId){
 		
	    Board board = boardService.getBoard(id);
	    Member member = memberService.getMember(principal.getName());
	    
	    if (parentId == null) { // 댓글 생성
	        Reply reply = replyService.createReply(board, replyDto.getContent(), member);
	        return String.format("redirect:/board/detail/%s#reply_%s", reply.getBoard().getId(), reply.getId());
	    } else { // 대댓글 생성
	        Reply parentReply = replyService.getAnswer(parentId);
	        replyService.createChildReply(board, parentId, replyDto.getContent(), member);
	        return String.format("redirect:/board/detail/%s#reply_%s", parentReply.getBoard().getId(), parentReply.getId());
	    }
 	}
	
    
    // 신고
    @PostMapping("/reply/report/{id}")
    public void report(@PathVariable("id") Long replyId) {
    	
    	Reply reply = replyRepository.findById(replyId).orElseThrow(() ->
    	new IllegalArgumentException("Invalid board ID"));
    	reply.incrementRepot();
    	this.replyRepository.save(reply);
    }

    
}