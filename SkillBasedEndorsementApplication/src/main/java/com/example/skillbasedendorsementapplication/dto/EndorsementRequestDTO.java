package com.example.skillbasedendorsementapplication.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndorsementRequestDTO {
    //one who is getting reviewed
    @NotNull(message = "Reviewee Id cannot be null")
    private Long revieweeId;

    //one who is reviewing
    @NotNull(message = "Reviewer Id cannot be null")
    private Long reviewerId;

    @NotNull(message = "Skill cannot be null")
    private String skill;

    @NotNull(message = "Score cannot be null")
    private int score;
}
