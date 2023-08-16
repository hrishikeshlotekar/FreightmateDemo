package com.freightmate.dto;

import com.freightmate.entity.SuburbPostcodeEntity;
import com.freightmate.entity.SuburbPostcodeId;
import lombok.*;
import org.springframework.stereotype.Component;

import javax.persistence.Column;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author Hrishikesh.Lotekar
 * @implNote SuburbPostcodeInfo DTO
 */
@Component
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuburbPostcodeInfo {
    @Size(min = 3, message = "Suburb name must have at least 3 characters")
    @Column(name = "suburb_name")
    private String suburbName;

    @NotNull
    @Min(value = 200, message = "Postcode must be between 200 and 9999.")
    @Max(value = 9999, message = "Postcode must be between 200 and 9999.")
    @Column(name = "postcode")
    private int postcode;

    /**
     * @param entity: passing entity parameter
     * Description : To Convert DTO to Entity
     */
    public static SuburbPostcodeInfo convertEntityToDTO(SuburbPostcodeEntity entity) {
        return SuburbPostcodeInfo.builder()
                .suburbName(entity.getId().getSuburbName())
                .postcode(entity.getId().getPostcode())
                .build();
    }

    /**
     * @param dto: passing dto parameter
     * Description : To Convert DTO to Entity
     * @implNote Given that SuburbPostcodeId is an Embeddable class,
     *    it does not inherently support the @Builder pattern in the same way as Entity classes do.
     *    In this case, I have manually created the instances of SuburbPostcodeId and then use them to build the SuburbPostcodeEntity.
     */

    public static SuburbPostcodeEntity convertDTOToEntity(SuburbPostcodeInfo dto) {
        SuburbPostcodeId id = new SuburbPostcodeId();
        id.setSuburbName(dto.getSuburbName());
        id.setPostcode(dto.getPostcode());

        SuburbPostcodeEntity entity = new SuburbPostcodeEntity();
        entity.setId(id);

        return entity;
    }
}

