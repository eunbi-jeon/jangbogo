package com.jangbogo.domain;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.jangbogo.domain.baseEntity.BaseAnswer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Reply extends BaseAnswer{
	
	
	// 대댓글 부모
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Reply parent;
	
	// 대댓글 자식, mappedBy 를 부모키에 연관관계를 맞춰야 부모 댓글을 삭제할때 자식 대댓글도 삭제됨
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<Reply> child = new ArrayList<>();
	
	private Integer depth;
	
	
	// 신고 받은 횟수
	private Integer report;
	
	// 신고 메소드
	public void incrementRepot() {
		if(report == null) {
			report = 1;
		}else {
			report ++;
		}
	}


}
