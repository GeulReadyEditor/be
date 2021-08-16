package com.encore.backend.dto;

import java.util.Date;
import java.util.List;

import com.encore.backend.vo.Comment;
import com.encore.backend.vo.Content;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class BoardDTO {
    @Id
    private String boardId;
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
