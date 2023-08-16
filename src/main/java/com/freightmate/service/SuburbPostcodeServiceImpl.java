package com.freightmate.service;
import com.freightmate.dto.SuburbNameInfo;
import com.freightmate.dto.SuburbPostcodeInfo;
import com.freightmate.entity.SuburbPostcodeEntity;
import com.freightmate.exception.ResourceNotFoundException;
import com.freightmate.repository.SuburbPostcodeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Hrishikesh.Lotekar
 * @implNote SuburbPostcodeServiceImpl Service class implementing SuburbPostcodeService interface methods
 */
@Service
public class SuburbPostcodeServiceImpl implements SuburbPostcodeService {


    private final SuburbPostcodeRepository suburbPostcodeRepository;

    /**
     * @param suburbPostcodeRepository: passing suburbPostcodeRepository parameter
     * Description : Implementing Constructor based dependency Injection.
     */
    public SuburbPostcodeServiceImpl(SuburbPostcodeRepository suburbPostcodeRepository) {
        this.suburbPostcodeRepository = suburbPostcodeRepository;


    }

    /**
     * @param postcode: passing Postcode parameter
     * Description : To Fetch the Suburbs details using postcode.
     */
    @Override
    public List<SuburbNameInfo> getSuburbsByPostcode(int postcode) throws ResourceNotFoundException
    {
        // Validations
        if (postcode < 200 || postcode > 9999) {
            throw new IllegalArgumentException("Postcode must be between 200 and 9999.");
        }
        // Process
        List<SuburbPostcodeEntity> suburbEntityList = suburbPostcodeRepository.findByIdPostcode(postcode);
        if (suburbEntityList.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No suburbs found for postcode %d.", postcode));
        }
        // response
        return suburbEntityList.stream()
                .map(SuburbNameInfo::convertEntityToDTOSuburbNameInfo)
                .toList();

    }

    /**
     * @param suburbName : passing Suburb Name parameter
     * Description : To Fetch the Postcode details using Suburb Name
     */
    @Override
    public Optional<List<Integer>> getPostcodesBySuburb(String suburbName) throws  ResourceNotFoundException{
        // Validations
        if (suburbName == null || suburbName.isEmpty()) {
            throw new IllegalArgumentException("Suburb name cannot be null or empty.");
        }
        // Process
        List<Integer> postcodes = suburbPostcodeRepository.findPostcodesBySuburbName(suburbName);
        if (postcodes.isEmpty()) {
            throw new ResourceNotFoundException(String.format("No Postcodes found for suburbName %s.", suburbName));
        }
        // response
        return Optional.of(postcodes);
    }

    /**
     * @param  suburbPostcodeDTO : passing suburbPostcodeDTO containing SuburbName and Postcode
     * Description : To Insert the combination of SuburbName with Postcode
     */
    @Override
    public SuburbPostcodeInfo addSuburbPostcode(SuburbPostcodeInfo suburbPostcodeDTO) {
        // Validations
        if (suburbPostcodeDTO.getSuburbName() == null || suburbPostcodeDTO.getPostcode() < 200 || suburbPostcodeDTO.getPostcode() > 9999) {
            throw new IllegalArgumentException("Invalid suburb name or postcode.");
        }

        //permission check
        //user permissions for create suburb and postcode.

        // Process
        SuburbPostcodeEntity suburbPostcode = SuburbPostcodeInfo.convertDTOToEntity(suburbPostcodeDTO);

        // Check if the combination already exists
        boolean combinationExists = suburbPostcodeRepository.existsById(suburbPostcode.getId());
        if (combinationExists) {
            throw new IllegalArgumentException("Suburb and postcode combination already exists.");
        }

        // Save the new combination
        SuburbPostcodeEntity savedSuburbPostcode = suburbPostcodeRepository.save(suburbPostcode);

        // Convert saved entity back to DTO and return response
        return SuburbPostcodeInfo.convertEntityToDTO(savedSuburbPostcode);
    }

}
