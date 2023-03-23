package com.jangbogo.domain.common;

import lombok.Getter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;

@EntityListeners(value = {AuditingEntityListener.class})
@MappedSuperclass //부모 타입의 Entity라고 선언 (실제로 테이블이 생성되지는 않음)
@Getter
public abstract class BaseEntity {

    @Column(updatable = false)
    @CreatedBy
    private String createBy;

    @LastModifiedBy
    private String modifiedBy;
}

