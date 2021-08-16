package com.encore.backend.controller;

import java.util.List;
import java.util.Map;

import com.encore.backend.dto.BoardDTO;
import com.encore.backend.service.BoardService;
import com.encore.backend.vo.BoardVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*", maxAge = 3600)
public class BoardController {
    private BoardService service;

    @Autowired
    public BoardController(BoardService service) {
        this.service = service;
    }

    @GetMapping("/board")
    public ResponseEntity<List<BoardVO>> getBoard(@RequestParam Map<String, Object> parameters) {
        List<BoardVO> list = service.selectBoard(parameters);
        return ResponseEntity.status(list == null ? HttpStatus.NOT_FOUND : HttpStatus.OK).body(list);
    }

    @GetMapping("/board/count")
    public ResponseEntity<Long> getBoardCount(@RequestParam Map<String, Object> parameters) {
        long count = service.getBoardsCount(parameters);
        return ResponseEntity.status(HttpStatus.OK).body(count);
    }

    @PostMapping("/board")
    public ResponseEntity<String> insertBoard(@RequestPart BoardDTO board, @RequestPart MultipartFile titleImageFile) {
        boolean result = service.upsertBoard(board, titleImageFile);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("insert board " + (result ? "suceess" : "fail"));
    }

    @PutMapping("/board")
    public ResponseEntity<String> updateBoard(@RequestPart BoardDTO board, @RequestPart MultipartFile titleImageFile) {
        boolean result = service.upsertBoard(board, titleImageFile);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("update board " + (result ? "suceess" : "fail"));
    }

    @DeleteMapping("/board")
    public ResponseEntity<String> deleteBoard(@RequestBody Map<String, Object> parameters) {
        boolean result = service.deleteBoard((String) parameters.get("boardId"));
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("delete board " + (result ? "suceess" : "fail"));
    }

}
