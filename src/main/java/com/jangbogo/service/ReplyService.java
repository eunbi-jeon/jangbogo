package com.jangbogo.service;

import java.util.List;
import java.util.Optional;

import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.Reply;

public interface ReplyService {
	
	// 댓글 생성
	Reply createReply(Board board, String content, Member nickName);
	
	// 대댓글 생성
	Reply createChildReply(Board board, Long parentId, String content, Member nickName);
	
	// 댓글 삭제
	void deleteReply(Reply reply);
	
	// 댓글 조회
	Reply getReply(Long id);
	
	// 댓글 수정
	void modifyReply(Long replyId, String content);
	
	

}
