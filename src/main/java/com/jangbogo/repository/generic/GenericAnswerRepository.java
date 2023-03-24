package com.jangbogo.repository.generic;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jangbogo.domain.baseEntity.BaseAnswer;

public interface GenericAnswerRepository <T extends BaseAnswer> extends JpaRepository<T, Long> {

}
