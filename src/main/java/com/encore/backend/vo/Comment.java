package com.encore.backend.vo;

import java.util.Date;

import org.springframework.data.annotation.Id;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Comment {
    @Id
    private String id;
    private String userId;
    private String nickName;
    private String comment;
    private Date modDatetime;

    public Comment(String id, String userId, String nickName, String comment, Date modDatetime) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
        this.comment = comment;
        this.modDatetime = modDatetime;
    }

    public Comment(String id, String userId, String nickName, String comment) {
        this.id = id;
        this.userId = userId;
        this.nickName = nickName;
        this.comment = comment;
    }

    public Comment(String userId, String nickName, String comment, Date modDatetime) {
        this.userId = userId;
        this.nickName = nickName;
        this.comment = comment;
        this.modDatetime = modDatetime;
    }

    public Comment(String id) {
        this.id = id;
    }

}
