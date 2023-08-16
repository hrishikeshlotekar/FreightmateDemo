package com.freightmate.controller;

import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.freightmate.dto.SuburbNameInfo;
import com.freightmate.dto.SuburbPostcodeInfo;
import com.freightmate.service.SuburbPostcodeServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


/**
 * @author Hrishikesh.Lotekar
 * @implNote SuburbPostcodeControllerTest Controller Test Class
 */
@WebMvcTest(SuburbPostcodeController.class)
class SuburbPostcodeControllerTest {

    @MockBean
    private SuburbPostcodeServiceImpl suburbPostcodeService;

    @Autowired
    private MockMvc mockMvc;

    /**
     * Description : Valid Postcode with Existing Suburbs
     */
@Test
void testGetSuburbsByPostcode_ValidPostcodeWithExistingSuburbs() throws Exception {
    int validPostcode = 3000;

    // Prepare a list of expected suburbs
    List<SuburbNameInfo> expectedSuburbs = new ArrayList<>();
    expectedSuburbs.add(new SuburbNameInfo("Suburb1"));
    expectedSuburbs.add(new SuburbNameInfo("Suburb2"));

    // Mock the behavior of the service
    when(suburbPostcodeService.getSuburbsByPostcode(validPostcode)).thenReturn(expectedSuburbs);

    // Perform the GET request and assert the response
    mockMvc.perform(get("/api/v1/suburbs/{postcode}", validPostcode))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            // Adjust the JSON path based on your actual response structure
            .andExpect(jsonPath("$.size()").value(expectedSuburbs.size()));
}

    /**
     * Description : Valid suburb with existing postcode
     */
    @Test
    void testGetPostcodesBySuburb_ValidSuburbNameWithExistingPostcodes() throws Exception {
        String validSuburbName = "ValidSuburb";
        List<Integer> expectedPostcodes = new ArrayList<>();
        when(suburbPostcodeService.getPostcodesBySuburb(validSuburbName)).thenReturn(Optional.of(expectedPostcodes));

        mockMvc.perform(get("/api/v1/postcodes").param("suburbName", validSuburbName))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").value(expectedPostcodes));
    }

    /**
     * Description : Test the valid combination of Suburb and Postcode
     */

    @Test
    void testAddSuburbPostcode_ValidCombination() throws Exception {
        SuburbPostcodeInfo validDTO = new SuburbPostcodeInfo("Valid Suburb", 3000);
        SuburbPostcodeInfo expectedResponse = new SuburbPostcodeInfo(validDTO.getSuburbName(), validDTO.getPostcode());
        when(suburbPostcodeService.addSuburbPostcode(validDTO)).thenReturn(expectedResponse);

        mockMvc.perform(post("/api/v1/suburbs").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(validDTO)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.suburbName").value(expectedResponse.getSuburbName()))
                .andExpect(jsonPath("$.postcode").value(expectedResponse.getPostcode()));
    }

    /**
     * Description : Test the Invalid combination of Suburb and Postcode
     */
    @Test
    void testAddSuburbPostcode_InvalidCombination() throws Exception {
        // Test case for handling validation errors
        SuburbPostcodeInfo invalidDTO = new SuburbPostcodeInfo("", -1);
        mockMvc.perform(post("/api/v1/suburbs").contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(invalidDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        verifyNoInteractions(suburbPostcodeService);
    }

    /**
     * Description : Utility method to convert objects to JSON string
     */
    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
