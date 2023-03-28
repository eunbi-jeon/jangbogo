package com.jangbogo.service;

import org.springframework.data.domain.Page;

import com.jangbogo.domain.Board;
import com.jangbogo.domain.Member;
import com.jangbogo.domain.Reply;
import com.jangbogo.service.generic.GenericAnswerService;

public interface ReplyService extends GenericAnswerService<Reply> {
	
	// 댓글 생성
	Reply createReply(Board board, String content, Member nickName);
	
	// 대댓글 생성
	Reply createChildReply(Board board, Long parentId, String content, Member nickName);
	
	// 댓글 대댓글 정렬
	Page<Reply> getList(int page);

}
