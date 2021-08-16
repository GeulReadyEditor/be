package com.encore.backend.controller;

import java.util.List;
import java.util.Map;

import com.encore.backend.service.BoardService;
import com.encore.backend.vo.Comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
@RequestMapping(value = "/comment")
public class CommentController {
    private BoardService service;

    @Autowired
    public CommentController(BoardService service) {
        this.service = service;
    }

    @GetMapping()
    public ResponseEntity<List<Comment>> getComment(@RequestParam Map<String, Object> parameters) {
        List<Comment> comments = service.getComment(parameters);
        return ResponseEntity.status(comments == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(comments);
    }

    @PostMapping()
    public ResponseEntity<String> insertComment(@RequestBody Map<String, Object> parameters) {
        boolean result = service.insertComment(parameters);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("update comment " + (result ? "suceess" : "fail"));
    }

    @DeleteMapping()
    public ResponseEntity<String> removeComment(@RequestBody Map<String, Object> parameters) {
        boolean result = service.removeComment(parameters);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("delete comment " + (result ? "suceess" : "fail"));
    }

    @PutMapping()
    public ResponseEntity<String> updateComment(@RequestBody Map<String, Object> parameters) {
        boolean result = service.updateComment(parameters);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("update comment " + (result ? "suceess" : "fail"));
    }
}
