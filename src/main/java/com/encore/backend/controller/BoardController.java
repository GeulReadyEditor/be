package com.encore.backend.controller;

import java.util.List;

import com.encore.backend.service.BoardService;
import com.encore.backend.vo.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/board")
public class BoardController {
    private BoardService service;

    @Autowired
    public BoardController(BoardService service) {
        this.service = service;
    }

    @GetMapping("/get")
    public List<Board> getBoard() {
        return service.selectBoard();
    }
}
