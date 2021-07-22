package com.encore.backend.service;

import java.util.List;

import com.encore.backend.repository.BoardRepository;
import com.encore.backend.vo.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BoardService {
    private BoardRepository repo;

    @Autowired
    public BoardService(BoardRepository repo) {
        this.repo = repo;
    }

    public List<Board> selectBoard() {
        List<Board> list = repo.findAll();
        return list;
    }
}
