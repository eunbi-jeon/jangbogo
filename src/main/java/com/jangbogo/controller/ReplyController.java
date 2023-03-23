package com.jangbogo.controller;

import java.security.Principal;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.server.ResponseStatusException;

import com.jangbogo.domain.Answer;
import com.jangbogo.domain.Reply;
import com.jangbogo.dto.ReplyDto;
import com.jangbogo.repository.AnswerRepository;
import com.jangbogo.repository.ReplyRepository;
import com.jangbogo.service.BoardService;
import com.jangbogo.service.MemberService;
import com.jangbogo.service.ReplyServiceImpl;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Controller
public class ReplyController {
	
private final BoardService boardservice;
private final ReplyServiceImpl replyservice;
private final MemberService memberservice;
private final ReplyRepository replyRepository;
	

	// 댓글 달기
//	@PostMapping("/reply/create/{id}")
// 	public String createReply(Model model, @PathVariable("id") Long id, @Valid
// 			ReplyFormDTO replyForm, BindingResult bindingResult, Principal principal){
// 		
// 		Board board = this.boardservice.getBoard(id);
// 		Member member = this.memberservice.getMember(principal.getName());
// 		
// 		if(bindingResult.hasErrors()) {
// 			model.addAttribute("board", board);
// 			return "board_detail";
// 		}
		
//		Reply reply = this.replyservice.createReply(board, replyForm.getContent(), member);
//		return String.format("redirect:/board/detail/$s#reply_%s", reply.getBoard().getId(), reply.getId());
// 	}
 
	
	//댓글수정 댓글 수정 form 으로 보냄
    @GetMapping("/reply/modify/{id}")
    public String modifyReply(ReplyDto replyForm, @PathVariable("id") Long id, Principal principal) {
        
    	Reply reply = this.replyservice.getReply(id);
    	
        if (!reply.getAuthor().getNickName().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        replyForm.setContent(reply.getContent());
        return "reply_form";
    }
    
    // 댓글수정 처리
//    @PostMapping("/reply/modify/{id}")
//    	public String replyModify(@Valid ReplyFormDTO replyFormDto, BindingResult bindingResult, @PathVariable("id") Long id, Principal principal) {
//    		if(bindingResult.hasErrors()) {
//    			return "reply_form";
//    		}
//    		Reply reply = this.replyservice.getReply(id);
//    		if(!reply.getAuthor().getNickName().equals(principal.getName())) {
//    			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
//    		}
//    		this.replyservice.replyModify(reply, replyFormDto.getContent());
//    		
//    		return String.format("redirect:/board/detail/%s#reply_%s", reply.getBoard().getId, reply.getId());
//    	}
    
    //댓글 삭제 
//	 @GetMapping("/reply/delete/{id}") 
//	 public String replyDelete(Principal principal, @PathVariable("id") Long id) {
//	 
//	 Reply reply = this.replyservice.getReply(id);
//	 
//	 if (!reply.getAuthor().getNickName().equals(principal.getName())) { throw
//	 new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다."); }
//	 
//	 this.replyservice.delete(reply);
//	 
//	 return String.format("redirect:/board/detail/%s", reply.getBoard().getId());
//	 
//	 }
    
    // 신고
    @PostMapping("/reply/{id}/report")
    public void report(@PathVariable Long replyId) {
    	
    	Reply reply = replyRepository.findById(replyId).orElseThrow(() -> new IllegalArgumentException("Invalid post ID"));
    	reply.incrementRepot();
    	this.replyRepository.save(reply);
    }
    
}