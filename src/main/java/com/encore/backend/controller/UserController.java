package com.encore.backend.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.encore.backend.dto.UserDto;
import com.encore.backend.service.UserService;
import com.encore.backend.vo.ResponseUser;
import com.encore.backend.vo.UserVO;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
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
@RequestMapping("/")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody UserDto userDto) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        userService.createUser(userDto);
        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);
    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers() {
        Iterable<UserVO> userList = userService.getUserByAll();
        List<ResponseUser> responseUsers = new ArrayList<ResponseUser>();
        userList.forEach(v -> {
            responseUsers.add(new ModelMapper().map(v, ResponseUser.class));
        });
        return ResponseEntity.status(HttpStatus.OK).body(responseUsers);
    }

    @GetMapping("/users/{email}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable String email) {
        UserDto userDto = userService.getUserDetailsByEmail(email);
        ResponseUser responseUser = new ModelMapper().map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @DeleteMapping("/users/{email}")
    public ResponseEntity<ResponseUser> deleteUser(@PathVariable String email) {
        boolean userDto = userService.deleteUserByEmail(email);
        ResponseUser responseUser = new ModelMapper().map(userDto, ResponseUser.class);
        return ResponseEntity.status(HttpStatus.OK).body(responseUser);
    }

    @GetMapping("/scraps/{email}")
    public ResponseEntity<List<String>> getUserScraps(@PathVariable String email,
            @RequestParam Map<String, Object> parameters) {
        int scrapPageNumber = Integer.parseInt((String) parameters.get("pageNumber"));
        List<String> scraps = userService.getUserScraps(email, scrapPageNumber);
        return ResponseEntity.status(HttpStatus.OK).body(scraps);
    }
}
