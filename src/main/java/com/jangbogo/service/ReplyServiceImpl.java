package com.jangbogo.service;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jangbogo.domain.Answer;
import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.Reply;
import com.jangbogo.repository.AnswerRepository;
import com.jangbogo.repository.ReplyRepository;
import com.jangbogo.service.generic.GenericAnswerServiceImpl;

import lombok.RequiredArgsConstructor;

@Service
public class ReplyServiceImpl extends GenericAnswerServiceImpl<Reply> implements ReplyService {
	
	private final ReplyRepository replyRepository;
	 
    public ReplyServiceImpl(ReplyRepository replyRepository) {
        super(replyRepository);
        this.replyRepository = replyRepository;
    }
    
	// 부모 댓글 생성
	@Override
	 public Reply createReply(Board board, String content, Member nickName) {
	 
		Reply reply = new Reply();
		
		reply.setBoard(board);
		reply.setContent(content);
		reply.setAuthor(nickName); 
		reply.setCreateAt(LocalDateTime.now());
		reply.setReport(0);
		reply.setDepth(0);
		
		return this.replyRepository.save(reply);
	  
	 }
	
	// 자식 댓글 생성
	@Override
	public Reply createChildReply(Board board, Long parentId, String content, Member nickName) {
		
		Reply parentReply = replyRepository.findById(parentId).orElseThrow(
	            () -> new IllegalArgumentException("부모 댓글이 존재하지 않습니다."));
		
		Reply childReply = new Reply();
		childReply.setBoard(board);
		childReply.setContent(content);
		childReply.setAuthor(nickName);
		childReply.setCreateAt(LocalDateTime.now());
		childReply.setReport(0);
		childReply.setDepth(parentReply.getDepth() + 1);
		childReply.setParent(parentReply);
		
		return replyRepository.save(childReply);
	}
	

}
