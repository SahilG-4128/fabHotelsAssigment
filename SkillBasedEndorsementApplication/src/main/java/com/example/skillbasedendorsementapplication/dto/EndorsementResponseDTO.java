package com.example.skillbasedendorsementapplication.dto;

import com.example.skillbasedendorsementapplication.models.Endorsement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndorsementResponseDTO {
    private Long id;
    private Long revieweeId;
    private Long reviewerId;
    private String skill;
    private int score;
    private double adjustedScore;
    private String reason;

    public EndorsementResponseDTO(Endorsement endorsement) {
        this.id = endorsement.getId();
        this.revieweeId = endorsement.getEndorsee().getId();
        this.reviewerId = endorsement.getEndorser().getId();
        this.skill = endorsement.getSkill().getName();
        this.score = endorsement.getScore();
        this.adjustedScore = endorsement.getAdjustedScore();
        this.reason = endorsement.getReason();
    }

}
