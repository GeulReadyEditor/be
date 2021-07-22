package com.encore.backend.vo;

import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "board")
@Data
public class Board {
    @Id
    private String id;
    private String user_id;
    private String nickname;
    private String title;
    private String subtitle;
    private String titleImage;
    private int likes;
    private String modDatetime;
    private List<Content> contents;
    private List<Comment> comments;
    private List<String> tags;
}
