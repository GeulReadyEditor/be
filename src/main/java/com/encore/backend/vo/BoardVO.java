package com.encore.backend.vo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "board")
@Data
public class BoardVO {
    @Id
    private String id;
    private String userEmail;
    private String nickName;
    private String title;
    private String subtitle;
    private String titleImage;
    private int likes;
    private Date modDatetime;
    private List<Content> contents;
    private List<Comment> comments;
    private List<String> tags;
}
