package com.freightmate.entity;

import lombok.*;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.*;
import java.io.Serializable;

/**
 * @author Hrishikesh.Lotekar
 * @implNote SuburbPostcodeId Embeddable Class
 */
@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class SuburbPostcodeId implements Serializable {

    @Size(min = 3, message = "Suburb name must have at least 3 characters")
    @Column(name = "suburb_name")
    private String suburbName;

    @NotNull
    @Min(value = 200, message = "Postcode must be between 200 and 9999.")
    @Max(value = 9999, message = "Postcode must be between 200 and 9999.")
    @Column(name = "postcode")
    private int postcode;

}

