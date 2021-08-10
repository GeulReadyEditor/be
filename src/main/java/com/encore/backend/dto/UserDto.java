package com.encore.backend.dto;

import java.util.List;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class UserDto {
    @NotNull(message = "Email cannot be null")
    @Size(min = 2, message = "Email not be less than two characters")
    @Email
    private String email;
    @NotNull(message = "Name cannot be null")
    @Size(min = 2, message = "Name not be less than two characters")
    private String nickName;
    private MultipartFile profileImageFile;
    private String profileImage;

    private List<String> scraps;
    private List<String> likes;
    private List<String> followers;
    private List<String> followings;
    private List<String> tags;
}
