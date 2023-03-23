package com.jangbogo.service.generic;

import com.jangbogo.domain.baseEntity.BaseBoard;
import com.jangbogo.repository.generic.GenericBoardRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class GenericBoardServiceImpl<T extends BaseBoard> implements GenericBoardService<T>{

    @Autowired
    protected GenericBoardRepository<T> genericBoardRepository;

    @Override
    public T create(T t) {
        genericBoardRepository.save(t);
        return null;
    }

    @Override
    public List<T> getList() {
        return genericBoardRepository.findAll();
    }

    @Override
    public void delete(Long id) {
        genericBoardRepository.deleteById(id);;
    }
}