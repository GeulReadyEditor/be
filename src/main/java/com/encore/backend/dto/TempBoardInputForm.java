package com.encore.backend.dto;

import lombok.Data;

@Data
public class TempBoardInputForm {

    private String userId;
    private String tempBoardId;
    private Integer pageNumber;

}
