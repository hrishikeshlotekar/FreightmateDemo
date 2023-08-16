package com.freightmate.service;

import com.freightmate.dto.SuburbNameInfo;
import com.freightmate.dto.SuburbPostcodeInfo;

import java.util.List;
import java.util.Optional;

/**
 * @author Hrishikesh.Lotekar
 * @implNote SuburbPostcodeService interface
 *
 */
public interface SuburbPostcodeService {

    /**
     * @param postcode: passing Postcode parameter
     * Description : To Fetch the Suburbs details using postcode.
     */
    List<SuburbNameInfo> getSuburbsByPostcode(int postcode);

    /**
     * @param suburbName : passing Suburb Name parameter
     * Description : To Fetch the Postcode details using Suburb Name
     */
    Optional<List<Integer>> getPostcodesBySuburb(String suburbName);

    /**
     * @param  suburbPostcodeDTO : passing suburbPostcodeDTO containing SuburbName and Postcode
     * Description : To Insert the combination of SuburbName with Postcode
     */

    SuburbPostcodeInfo addSuburbPostcode(SuburbPostcodeInfo suburbPostcodeDTO);
}
