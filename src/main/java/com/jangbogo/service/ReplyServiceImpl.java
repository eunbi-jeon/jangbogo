package com.jangbogo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.stereotype.Service;

import com.jangbogo.domain.Reply;
import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.common.BaseTimeEntity;
import com.jangbogo.repository.ReplyRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class ReplyServiceImpl implements ReplyService{
	
	private final ReplyRepository replyRepository;
	
	
	// 댓글 만들기
	@Override
	 public Reply createReply(Board board, String content, Member nickName) {
	  
	 Reply reply = new Reply(); 
	 reply.setBoard(board); 
	 reply.setContent(content);
	 reply.setAuthor(nickName); 
	 reply.setCreateAt(LocalDateTime.now());
	  
	 this.replyRepository.save(reply);
	  
	 return reply;
	  
	 }
	
	// 댓글 삭제
	@Override
	public void deleteReply(Reply reply) {
		this.replyRepository.delete(reply);
	}
	 
	 // 댓글 조회
	@Override
	public Reply getReply(Long id) {
		 
		Optional<Reply> reply = this.replyRepository.findById(id);
		 
		return reply.get();
		
	}

	
	// 댓글 수정
	@Override
	public void modifyReply(Reply reply, String content) {
		
		reply.setContent(content);
		reply.setModifiedAt(LocalDateTime.now());
		this.replyRepository.save(reply);
	}
	
	// 댓글 삭제
	public void delete(Reply reply) {
		this.replyRepository.delete(reply);
	}
	
	// 댓글 신고
	public void report(Reply reply, Member member) {
		reply.getReport().add(member);
		this.replyRepository.save(reply);
	}
	

}
