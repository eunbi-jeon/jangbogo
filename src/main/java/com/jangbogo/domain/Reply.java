package com.jangbogo.domain;


import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.jangbogo.domain.common.BaseTimeEntity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class Reply extends BaseTimeEntity{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 500)
	private String content;
	
	@ManyToOne		
	private Board board;
	
	@ManyToOne 
    private Member author;
	
	// 신고 받은 횟수
	private Integer report;
	
	public void incrementRepot() {
		if(report == null) {
			report = 1;
		}else {
			report ++;
		}
	}


}
