package com.encore.backend.vo;

import java.util.Date;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Document(collection = "temp_board")
@Data
public class TempBoard {
    @Id
    private String id;
    private String user_id;
    private String title;
    private String subtitle;
    private String titleImage;
    private Date modDatetime;
    private List<Content> contents;
    private List<Memo> memos;
    private List<String> tags;
}
