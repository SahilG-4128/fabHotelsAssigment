package com.example.skillbasedendorsementapplication.service;

import com.example.skillbasedendorsementapplication.dto.EndorsementRequestDTO;
import com.example.skillbasedendorsementapplication.dto.EndorsementResponseDTO;

import java.util.List;
import java.util.Map;

public interface EndrosementService {

    public EndorsementResponseDTO postEndorsement(EndorsementRequestDTO request);

    public Map<String, List<String>> getEndorsements(Long userId);

}
