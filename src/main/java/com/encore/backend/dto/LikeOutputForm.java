package com.encore.backend.dto;

import lombok.Data;

@Data
public class LikeOutputForm {
    Integer boardLikes;

    public LikeOutputForm(Integer boardLikes) {
        this.boardLikes = boardLikes;
    }
}
