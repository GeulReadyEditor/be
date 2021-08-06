package com.encore.backend.vo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "users")
@Data
public class UserVO {
    @Id
    private String userId;
    private String email;
    private String name;
    private String nickName;
    private String intro;
    private String profileImage;
    private String encryptedPwd;
    private List<String> scraps;
    private List<String> likes;
    private List<String> followers;
    private List<String> followings;
    private List<String> tags;
}