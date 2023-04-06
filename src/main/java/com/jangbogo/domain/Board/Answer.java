package com.jangbogo.domain.Board;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

<<<<<<< HEAD
=======
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
import com.jangbogo.domain.common.BaseTimeEntity;
import com.jangbogo.domain.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Answer extends BaseTimeEntity {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 4000)
	private String content; 
	
	// 대댓글 부모
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "parent_id")
	private Answer parent;
	
	@OneToMany(mappedBy = "parent", fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE}, orphanRemoval = true)
	private List<Answer> child = new ArrayList<>();
	
	private Integer depth;
	
<<<<<<< HEAD
	@ManyToOne			
=======
	@ManyToOne		
	@JsonBackReference
>>>>>>> be985e41549ba282b5d80546d617aeb64b2a5333
	private Question question;

	@ManyToOne
    private Member name;
	
	// 추천
	@ManyToMany
    Set<Member> voter;
	
	// 신고
	@ManyToMany
	Set<Member> report= new HashSet<>();
}
