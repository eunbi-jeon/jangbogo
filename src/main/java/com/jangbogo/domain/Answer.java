package com.jangbogo.domain;

import javax.persistence.Entity;
import javax.persistence.Table;

import com.jangbogo.domain.baseEntity.BaseAnswer;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@Table(name = "answer")
public class Answer extends BaseAnswer{
	

}
