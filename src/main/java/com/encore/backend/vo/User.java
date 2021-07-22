package com.encore.backend.vo;

import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "user")
@Data
public class User {
    private String id;
    private String email;
    private String name;
    private String nickname;
    private String intro;
    private String profileImage;
    private List<String> scraps;
    private List<String> likes;
    private List<String> followers;
    private List<String> followings;
    private List<String> tags;
}
