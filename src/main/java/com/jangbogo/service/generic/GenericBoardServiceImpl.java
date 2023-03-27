package com.jangbogo.service.generic;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;

import com.jangbogo.DataNotFoundException;
import com.jangbogo.domain.baseEntity.BaseBoard;
import com.jangbogo.repository.generic.GenericBoardRepository;

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
    
    @Override
    public T getBoard(Long id) {
        Optional<T> board=genericBoardRepository.findById(id);
        if(board.isPresent()){
            return board.get();
        }else{
            throw new DataNotFoundException("요청한 파일을 찾지 못했습니다.");
        }
    }
}