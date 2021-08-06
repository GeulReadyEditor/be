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
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FollowerController {
    private UserService userService;

    @Autowired
    public FollowerController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/followers/{email}")
    public ResponseEntity<List<String>> getUserScraps(@PathVariable String email) {
        List<String> scraps = userService.getUserFollowers(email);
        return ResponseEntity.status(scraps == null ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(scraps);
    }

    @PostMapping("/followers/{email}")
    public ResponseEntity<String> addUserFollwer(@PathVariable String email,
            @RequestBody Map<String, Object> parameters) {
        String follower = (String) parameters.get("followerEmail");
        boolean result = userService.addUserFollwers(email, follower);

        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("insert scrap to user " + (result ? "suceess" : "fail"));
    }

    @DeleteMapping("/followers/{email}")
    public ResponseEntity<String> removeUserFollower(@PathVariable String email,
            @RequestBody Map<String, Object> parameters) {
        String followerEmail = (String) parameters.get("followerEmail");
        boolean result = userService.removeUserFollwers(email, followerEmail);

        return ResponseEntity.status(result ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("remove scrap to user " + (result ? "suceess" : "fail"));
    }
}
