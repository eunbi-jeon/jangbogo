package com.jangbogo.service.generic;

import com.jangbogo.domain.baseEntity.BaseBoard;

import java.util.List;

public interface GenericBoardService<T extends BaseBoard> {
    T create(T t);
    List<T> getList();

    void delete(Long id);
}