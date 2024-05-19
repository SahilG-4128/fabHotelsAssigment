package com.example.skillbasedendorsementapplication.controller;

import com.example.skillbasedendorsementapplication.dto.EndorsementRequestDTO;
import com.example.skillbasedendorsementapplication.dto.EndorsementResponseDTO;
import com.example.skillbasedendorsementapplication.exceptionhandling.CustomException;
import com.example.skillbasedendorsementapplication.service.EndrosementService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/endorsements")
@Slf4j
public class EndorsementController {
    private final EndrosementService endrosementService;

    @Autowired
    public EndorsementController(EndrosementService endrosementService){
        this.endrosementService=endrosementService;
    }

    @PostMapping
    public EndorsementResponseDTO postEndorsement(@Valid @RequestBody EndorsementRequestDTO request) {
        try{
            log.info("call made for posting endorsement successfully");
            if(Objects.equals(request.getRevieweeId(), request.getReviewerId())){
                EndorsementResponseDTO endorsementResponseDTO=new EndorsementResponseDTO();
                endorsementResponseDTO.setRevieweeId(null);
                endorsementResponseDTO.setReviewerId(null);
                endorsementResponseDTO.setSkill("");
                endorsementResponseDTO.setScore(request.getScore());
                endorsementResponseDTO.setAdjustedScore(request.getScore());
                endorsementResponseDTO.setReason("ReviewerId and RevieweeId are same");
                log.warn("reviewer and reviewee id are same");
                return endorsementResponseDTO;
            }
            return endrosementService.postEndorsement(request);
        }
        catch(Exception e){
            throw new CustomException("Failed in making POST request",e.getCause());
        }
    }

    @GetMapping("/{userId}")
    public Map<String, List<String>> getEndorsements(@Valid @PathVariable Long userId) {
        try{
            log.info("call made for posting endorsement successfully for the userid:"+userId);
            return endrosementService.getEndorsements(userId);
        }
        catch(Exception e){
            throw new CustomException("Failed to make GET request", e.getCause());
        }
    }


}
