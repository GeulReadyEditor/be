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
public class FollowController {
    private UserService userService;

    @Autowired
    public FollowController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/followers/{email}")
    public ResponseEntity<List<String>> getUserFollwers(@PathVariable String email) {
        List<String> scraps = userService.getUserFollowers(email);
        return ResponseEntity.status(scraps == null ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(scraps);
    }

    @GetMapping("/followings/{email}")
    public ResponseEntity<List<String>> getUserFollowings(@PathVariable String email) {
        List<String> scraps = userService.getUserFollowings(email);
        return ResponseEntity.status(scraps == null ? HttpStatus.NO_CONTENT : HttpStatus.OK).body(scraps);
    }

    @PostMapping("/followings/{email}")
    public ResponseEntity<String> addUserFollwing(@PathVariable String email,
            @RequestBody Map<String, Object> parameters) {
        String followEmail = (String) parameters.get("followEmail");
        boolean result1 = userService.addUserFollowings(email, followEmail);
        boolean result2 = userService.addUserFollowers(followEmail, email);

        return ResponseEntity.status(result1 && result2 ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("insert " + email + " follow to user " + (result1 && result2 ? "suceess" : "fail"));
    }

    @DeleteMapping("/followings/{email}")
    public ResponseEntity<String> removeUserFollower(@PathVariable String email,
            @RequestBody Map<String, Object> parameters) {
        String followEmail = (String) parameters.get("followEmail");
        boolean result1 = userService.removeUserFollowers(followEmail, email);
        boolean result2 = userService.removeUserFollowings(email, followEmail);

        return ResponseEntity.status(result1 && result2 ? HttpStatus.CREATED : HttpStatus.NO_CONTENT)
                .body("remove follow from user " + (result1 && result2 ? "suceess" : "fail"));
    }
}
