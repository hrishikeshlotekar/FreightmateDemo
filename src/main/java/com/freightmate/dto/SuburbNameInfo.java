package com.freightmate.dto;

import com.freightmate.entity.SuburbPostcodeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.Size;

/**
 * @author Hrishikesh.Lotekar
 * @implNote SuburbNameInfo DTO
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuburbNameInfo {
    @Size(min = 3, message = "Suburb name must have at least 3 characters")
    @Column(name = "suburb_name")
    private String suburbName;

    /**
     * @param entity: passing entity parameter
     * Description : To Convert Entity to DTO
     */
    public static SuburbNameInfo convertEntityToDTOSuburbNameInfo(SuburbPostcodeEntity entity) {
        return SuburbNameInfo.builder()
                .suburbName(entity.getId().getSuburbName())
                .build();
    }
}
