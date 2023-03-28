package com.jangbogo.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.Reply;
import com.jangbogo.repository.ReplyRepository;
import com.jangbogo.service.generic.GenericAnswerServiceImpl;

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
		reply.setNickName(nickName); 
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
		childReply.setNickName(nickName);
		childReply.setCreateAt(LocalDateTime.now());
		childReply.setReport(0);
		childReply.setDepth(parentReply.getDepth() + 1);
		childReply.setParent(parentReply);
		
		return replyRepository.save(childReply);
	}
	
	// 댓글 대댓글 정렬
	@Override
	public Page<Reply> getList(int page){
		
		//최신글을 먼저 출력 하기, 날짜 컬럼 (createDate) 을 desc 해서 출력 
		List<Sort.Order> sorts = new ArrayList(); 
		sorts.add(Sort.Order.desc("createAt")); 
	
		//Pageable 객체에 2개의 값을 담아서 메개변수로 던짐  , 10 <== 출력할 레코드 수 
		Pageable pageable = PageRequest.of(page, 10 , Sort.by(sorts)); 
		
		return this.replyRepository.findAllByOrderByParentIdDescDepthAscCreateAtDesc(pageable);
	}
	

}
