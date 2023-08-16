package com.freightmate.repository;

import com.freightmate.entity.SuburbPostcodeEntity;
import com.freightmate.entity.SuburbPostcodeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
/**
 * @author Hrishikesh.Lotekar
 * @implNote SuburbPostcodeRepository repository
 */
@Repository
public interface SuburbPostcodeRepository extends JpaRepository<SuburbPostcodeEntity, SuburbPostcodeId> {


    /**
     * @param postcode: passing Postcode parameter
     * Description : To Fetch the Suburbs details using postcode.
     */
    List<SuburbPostcodeEntity> findByIdPostcode(int postcode);


    /**
     * @param suburbName : passing Suburb Name parameter
     * Description : To Fetch the Postcode details using Suburb Name
     */
    @Query("SELECT s.id.postcode FROM SuburbPostcodeEntity s WHERE s.id.suburbName = :suburbName")
    List<Integer> findPostcodesBySuburbName(@Param("suburbName") String suburbName);



}

