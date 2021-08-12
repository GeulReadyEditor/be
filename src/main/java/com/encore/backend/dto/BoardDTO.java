package com.encore.backend.dto;

import java.util.Date;
import java.util.List;

import com.encore.backend.vo.Comment;
import com.encore.backend.vo.Content;

import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class BoardDTO {
    @Id
    private String id;
    private String userEmail;
    private String nickName;
    private String title;
    private String subtitle;
    private String titleImage;
    private MultipartFile titleImageFile;
    private int likes;
    private Date modDatetime;
    private List<Content> contents;
    private List<Comment> comments;
    private List<String> tags;
}
