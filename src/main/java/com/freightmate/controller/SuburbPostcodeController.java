package com.freightmate.controller;

import com.freightmate.dto.SuburbNameInfo;
import com.freightmate.dto.SuburbPostcodeInfo;
import com.freightmate.exception.ResourceNotFoundException;
import com.freightmate.service.SuburbPostcodeServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

/**
 * @author Hrishikesh.Lotekar
 * @implNote Rest Controller for Suburb and Postcode request and response
 */
@RestController
@RequestMapping("/api/v1")
public class SuburbPostcodeController {
    private final SuburbPostcodeServiceImpl suburbPostcodeService;
    private final Logger logger = LoggerFactory.getLogger(SuburbPostcodeController.class);

    /**
     * @param suburbPostcodeService: passing suburbPostcodeService parameter
     * Description : Implementing Constructor based dependency Injection.
     */
    public SuburbPostcodeController(SuburbPostcodeServiceImpl suburbPostcodeService) {
        this.suburbPostcodeService = suburbPostcodeService;
    }

    /**
     * @param postcode: passing Postcode parameter
     * Description : To Fetch the Suburbs details using postcode.
     */
    @GetMapping(value = "/suburbs/{postcode}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<SuburbNameInfo>> getSuburbsByPostcode(@PathVariable("postcode") int postcode) throws ResourceNotFoundException {
        logger.info("Received request to get suburbs for postcode: {}", postcode);

        List<SuburbNameInfo> suburbs = suburbPostcodeService.getSuburbsByPostcode(postcode);

        logger.info("Returning {} suburbs for postcode: {}", suburbs.size(), postcode);

        return new ResponseEntity<>(suburbs, HttpStatus.OK);
    }


    /**
     * @param suburbName : passing Suburb Name parameter
     * Description : To Fetch the Postcode details using Suburb Name
     */
    @GetMapping(value = "/postcodes", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Optional<List<Integer>>> getPostcodesBySuburb(@RequestParam String suburbName) throws ResourceNotFoundException {
        logger.info("Received request to get postcodes for suburb: {}", suburbName);

        Optional<List<Integer>> postcodes = suburbPostcodeService.getPostcodesBySuburb(suburbName);

        if (postcodes.isEmpty()) {
            logger.warn("No postcodes found for suburb: {}", suburbName);
            throw new ResourceNotFoundException(String.format("No postcodes found for suburb %s.", suburbName));
        }

        logger.info("Returning postcodes for suburb: {}", suburbName);

        return new ResponseEntity<>(postcodes, HttpStatus.OK);
    }


    /**
     * @param  suburbPostcodeDTO : passing suburbPostcodeDTO containing SuburbName and Postcode
     * Description : To Insert the combination of SuburbName with Postcode
     */
    @PostMapping(value = "/suburbs", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuburbPostcodeInfo> addSuburbPostcode(@Valid @RequestBody SuburbPostcodeInfo suburbPostcodeDTO)  {
        logger.info("Received request to add suburb and postcode: {}", suburbPostcodeDTO);

        SuburbPostcodeInfo addSuburbPostcode = suburbPostcodeService.addSuburbPostcode(suburbPostcodeDTO);

        logger.info("Added suburb and postcode: {}", addSuburbPostcode);

        return new ResponseEntity<>(addSuburbPostcode, HttpStatus.CREATED);
    }


}

