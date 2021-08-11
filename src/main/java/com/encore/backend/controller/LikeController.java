package com.encore.backend.controller;

import com.encore.backend.dto.LikeInputForm;
import com.encore.backend.dto.LikeOutputForm;
import com.encore.backend.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/{boardId}")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LikeController {

    LikeService likeService;

    @Autowired
    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @PostMapping
    public ResponseEntity<LikeOutputForm> updateLike(@PathVariable String boardId, @RequestBody LikeInputForm form) {
        if (form == null)
            throw new IllegalArgumentException();

        int boardLikes = likeService.updateBoardLikes(boardId, form.getUserEmail());
        return new ResponseEntity<>(new LikeOutputForm(Integer.valueOf(boardLikes)), HttpStatus.OK);
    }

}
