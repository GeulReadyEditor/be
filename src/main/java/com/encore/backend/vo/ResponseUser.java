package com.encore.backend.vo;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseUser {
    private String email;
    private String name;
    private String nickName;
    private String intro;
    private String profileImage;

    private List<String> scraps;
    private List<String> likes;
    private List<String> followers;
    private List<String> followings;
    private List<String> tags;
}
