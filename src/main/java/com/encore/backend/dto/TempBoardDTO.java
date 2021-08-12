package com.encore.backend.dto;

import java.util.Date;
import java.util.List;

import com.encore.backend.vo.Content;
import com.encore.backend.vo.Memo;

import org.springframework.data.annotation.Id;
import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class TempBoardDTO {
    @Id
    private String id;
    private String userEmail;
    private String title;
    private String subtitle;
    private String titleImage;
    private MultipartFile titleImageFile;
    private Date modDatetime;
    private List<Content> contents;
    private List<Memo> memos;
    private List<String> tags;
}
