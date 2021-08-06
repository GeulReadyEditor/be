package com.encore.backend.dto;

import lombok.Data;

@Data
public class TempBoardInputForm {

    private String userEmail;
    private String tempBoardId;
    private Integer pageNumber;

}
