package com.encore.backend.controller;

import java.util.List;
import java.util.Map;

import com.encore.backend.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/scraps/{email}")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ScrapController {
    private UserService userService;

    @Autowired
    public ScrapController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public ResponseEntity<List<String>> getUserScraps(@PathVariable String email,
            @RequestParam Map<String, Object> parameters) {
        int scrapPageNumber = Integer.parseInt((String) parameters.get("pageNumber"));
        List<String> scraps = userService.getUserScraps(email, scrapPageNumber);
        return ResponseEntity.status(scraps == null ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(scraps);
    }

    @GetMapping("/count")
    public ResponseEntity<Integer> getUserScrapsCount(@PathVariable String email,
            @RequestParam Map<String, Object> parameters) {
        int scrapsCount = userService.getUserScrapsCount(email);
        return ResponseEntity.status(HttpStatus.OK).body(scrapsCount);
    }

    @PostMapping("")
    public ResponseEntity<String> addUserScraps(@PathVariable String email,
            @RequestBody Map<String, Object> parameters) {
        String boardId = (String) parameters.get("boardId");
        boolean result = userService.addUserScraps(email, boardId);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("insert scrap to user " + (result ? "suceess" : "fail"));
    }

    @DeleteMapping("")
    public ResponseEntity<String> removeUserScraps(@PathVariable String email,
            @RequestBody Map<String, Object> parameters) {
        String boardId = (String) parameters.get("boardId");
        boolean result = userService.removeUserScraps(email, boardId);
        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("remove scrap from user " + (result ? "suceess" : "fail"));
    }
}
