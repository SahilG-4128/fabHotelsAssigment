package com.example.skillbasedendorsementapplication.controller;

import com.example.skillbasedendorsementapplication.dto.EndorsementRequestDTO;
import com.example.skillbasedendorsementapplication.dto.EndorsementResponseDTO;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class EndorsementControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testPostEndorsement() throws Exception {
        EndorsementRequestDTO request = new EndorsementRequestDTO();
        request.setReviewerId(1L);
        request.setRevieweeId(2L);
        request.setSkill("Java");
        request.setScore(6);

        MvcResult result = mockMvc.perform(post("/endorsements")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andReturn();

        EndorsementResponseDTO response = objectMapper.readValue(result.getResponse().getContentAsString(), EndorsementResponseDTO.class);

        assertEquals(1L, response.getReviewerId());
        assertEquals(2L, response.getRevieweeId());
        assertEquals("Java", response.getSkill());
        assertEquals(6, response.getScore());
    }

    @Test
    public void testGetEndorsements() throws Exception {
        long userId = 1L;

        MvcResult result = mockMvc.perform(get("/endorsements/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // The response is a Map where each key is a skill and the value is a list of endorsements
        Map<String, List<String>> responses = objectMapper.readValue(result.getResponse().getContentAsString(), new TypeReference<Map<String, List<String>>>() {});

        // Add assertions to verify the responses
        // For example, if you expect the map to have one key (one skill) for the user with id 1:
        assertEquals(2, responses.size());

        //also we can use something like
        //assertTrue(responses.size() > 0);

        // Get the first key in the map (assuming it's not empty)
        String firstKey = responses.keySet().iterator().next();

        // Get the list of endorsements for the first skill
        List<String> endorsements = responses.get(firstKey);

        // Now you can make assertions about the endorsements
        // For example, if you expect there to be at least one endorsement for the first skill:
        assertFalse(endorsements.isEmpty());
    }

}