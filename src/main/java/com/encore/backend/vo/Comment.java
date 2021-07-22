package com.encore.backend.vo;

import java.util.Date;

import lombok.Data;

@Data
public class Comment {
    private int no;
    private String userId;
    private String nickname;
    private String comment;
    private Date modDatetime;
}
