package com.example.skillbasedendorsementapplication.service.implementation;

import com.example.skillbasedendorsementapplication.dto.EndorsementRequestDTO;
import com.example.skillbasedendorsementapplication.dto.EndorsementResponseDTO;
import com.example.skillbasedendorsementapplication.exceptionhandling.CustomException;
import com.example.skillbasedendorsementapplication.models.Endorsement;
import com.example.skillbasedendorsementapplication.models.Skill;
import com.example.skillbasedendorsementapplication.models.User;
import com.example.skillbasedendorsementapplication.repository.EndrosementRepository;
import com.example.skillbasedendorsementapplication.repository.SkillRepository;
import com.example.skillbasedendorsementapplication.repository.UserRepository;
import com.example.skillbasedendorsementapplication.service.EndrosementService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class EndorsementServiceImpl implements EndrosementService {
    private final EndrosementRepository endrosementRepository;
    private final UserRepository userRepository;

    private final SkillRepository skillRepository;

    @Autowired
    public EndorsementServiceImpl(EndrosementRepository endrosementRepository,UserRepository userRepository,SkillRepository skillRepository){
        this.endrosementRepository=endrosementRepository;
        this.userRepository=userRepository;
        this.skillRepository=skillRepository;
    }

    @Override
    public EndorsementResponseDTO postEndorsement(EndorsementRequestDTO request) {
        try {
            User reviewee = userRepository.findById(request.getRevieweeId())
                    .orElseThrow(() -> new CustomException("Endrosee not found"));
            log.info("reviewee id checked successfully");
            User reviewer = userRepository.findById(request.getReviewerId())
                    .orElseThrow(() -> new CustomException("Endroser not found"));
            log.info("reviewer id checked successfully");
            Skill currSkill=skillRepository.findByName(request.getSkill());
            Skill skill = skillRepository.findById(currSkill.getId())
                    .orElseThrow(() -> new CustomException("Skill not found"));
            log.info("skill id checked successfully");
            log.info("all checks for endroser,endrosee and skills done.");
            StringBuilder reason=new StringBuilder();
            double adjustedScore = calculateAdjustedScore(reviewee, reviewer, skill, request.getScore(),reason);
            log.info("adjusted score calculated successfully");
            Endorsement endorsement = new Endorsement();
            endorsement.setEndorsee(reviewee);
            endorsement.setEndorser(reviewer);
            endorsement.setSkill(skill);
            endorsement.setScore(request.getScore());
            endorsement.setAdjustedScore(adjustedScore);
            endorsement.setReason(reason.toString());

            Endorsement response=endrosementRepository.save(endorsement);
            log.info("data saved after calculating the adjusted score");
            return new EndorsementResponseDTO(endorsement);
        }
        catch (Exception e){
            throw new CustomException("Error in posting endorsement: "+ e.getMessage());
        }
    }

    @Override
    public Map<String, List<String>>  getEndorsements(Long userId) {
        try{
            List<Endorsement> endorsements = endrosementRepository.findByEndorseeId(userId);
            log.info("endorsements fetched successfully from database");
            if(endorsements.isEmpty()){
                log.warn("no endorsements found for the userid:"+userId);
            }
            Map<String, List<String>> endorsementsBySkill = endorsements.stream()
                    .map(EndorsementResponseDTO::new)
                    .collect(Collectors.groupingBy(
                            EndorsementResponseDTO::getSkill,
                            Collectors.mapping(dto ->
                                            "P" + dto.getReviewerId() + " - " + dto.getScore() +
                                                    " (" + dto.getAdjustedScore() + " with reason: " + dto.getReason() + ")",
                                    Collectors.toList()
                            )
                    ));
            return endorsementsBySkill;

        }
        catch(Exception e){
            throw new CustomException("Error in retrieving endrosement: "+ e.getMessage());
        }
    }

    private double calculateAdjustedScore(User reviewee, User reviewer, Skill skill, int score,StringBuilder reason) {
        log.info("Calculating adjusted score of the reviwee id:"+reviewee.getId()+" by the reviewer id:"+reviewer.getId());
        double weight = 1.0;
        boolean revieweeHasSkill = reviewee.getSkills().stream().anyMatch(userSkillRelationship -> userSkillRelationship.getName().equals(skill.getName()));
        boolean reviewerHasSkill = reviewer.getSkills().stream().anyMatch(userSkillRelationship -> userSkillRelationship.getName().equals(skill.getName()));

        if (!reviewerHasSkill) {
            reason.append("Reviewer id: ").append(reviewer.getId()).append(" does not have skill: ").append(skill.getName()).append(" So,cannot review it.");
            log.warn("Reviewer id: " + reviewer.getId() + " does not have skill: " + skill.getName() + " and cannot review it.");
            return 0;
        }

        if(!revieweeHasSkill){
            reason.append("Reviewee id: ").append(reviewer.getId()).append(" does not have skill: ").append(skill.getName());
            log.warn("Reviewer id: " + reviewer.getId() + " does not have skill: " + skill.getName() + " and cannot review it.");
            return 0;
        }

        if (reviewee.getYearsOfExperience() > reviewer.getYearsOfExperience()) {
            reason.append("score need to be deducted since reviewer is less experienced, ");
            weight -= 0.5;
        }
        if (reviewerHasSkill) {
            reason.append("Reviewer has some experience of:").append(skill.getName());
            weight += 0.4;
        }
        if(weight < 0){
            log.info("final adjusted score:"+weight);
            return 0;
        }
        log.info("final adjusted score:"+weight);
        if(score * weight > score){
            return score;
        }
        else{
            return score * weight;
        }
    }
}
