package com.encore.backend.controller;

import java.util.List;
import java.util.Map;

import com.encore.backend.service.BoardService;
import com.encore.backend.vo.Board;
import com.encore.backend.vo.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BoardController {
    private BoardService service;

    @Autowired
    public BoardController(BoardService service) {
        this.service = service;
    }

    @GetMapping("/board")
    public ResponseEntity<List<Board>> getBoard(@RequestBody Map<String, Object> parameters) {
        List<Board> list = service.selectBoard(parameters);
        return ResponseEntity.status(list == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(list);
    }

    @PostMapping("/board")
    public ResponseEntity<String> insertBoard(@RequestBody Board board) {
        boolean result = service.insertBoard(board);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("insert board " + (result ? "suceess" : "fail"));
    }

    @PutMapping("/board")
    public ResponseEntity<String> updateBoard(@RequestBody Map<String, Object> parameters) {
        boolean result = service.updateBoard(parameters);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("update board " + (result ? "suceess" : "fail"));
    }

    @DeleteMapping("/board")
    public ResponseEntity<String> deleteBoard(@RequestBody Map<String, Object> parameters) {
        boolean result = service.deleteBoard((String) parameters.get("id"));
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("delete board " + (result ? "suceess" : "fail"));
    }

    @GetMapping("/comment")
    public ResponseEntity<List<Comment>> getComment(@RequestBody Map<String, Object> parameters) {
        List<Comment> comments = service.getComment(parameters);
        return ResponseEntity.status(comments == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(comments);
    }

    @PostMapping("/comment")
    public ResponseEntity<String> insertComment(@RequestBody Map<String, Object> parameters) {
        boolean result = service.insertComment(parameters);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("update comment " + (result ? "suceess" : "fail"));
    }

    @DeleteMapping("/comment")
    public ResponseEntity<String> removeComment(@RequestBody Map<String, Object> parameters) {
        boolean result = service.removeComment(parameters);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("delete comment " + (result ? "suceess" : "fail"));
    }

    @PutMapping("/comment")
    public ResponseEntity<String> udpateComment(@RequestBody Map<String, Object> parameters) {
        boolean result = service.updateComment(parameters);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("update comment " + (result ? "suceess" : "fail"));
    }
}
