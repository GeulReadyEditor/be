package com.encore.backend.controller;

import com.encore.backend.dto.LikeInputForm;
import com.encore.backend.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{boardId}")
public class LikeController {

    LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<Integer> updateLike(@PathVariable String boardId, @RequestBody LikeInputForm form) {
        if(form == null) throw new IllegalArgumentException();

        int boardLikes = likeService.updateBoardLikes(boardId, form.getUserEmail());
        return new ResponseEntity<>(Integer.valueOf(boardLikes), HttpStatus.OK);
    }

}
