package com.encore.backend.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class UserDto {
    private String userId;
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String name;
    private String nickName;
    private String intro;
    private String profileImage;

    @NotNull(message = "Password cannot be null")
    @Size(min = 8, message = "Password not be less than 8 characters")
    private String pwd;
    private List<String> scraps;
    private List<String> likes;
    private List<String> followers;
    private List<String> followings;
    private List<String> tags;
}
