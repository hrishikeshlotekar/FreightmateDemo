package com.freightmate.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;

/**
 * @author Hrishikesh.Lotekar
 * @implNote SuburbPostcodeEntity Entity Class
 */
@Entity
@Table(name = "SuburbPostcode")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SuburbPostcodeEntity implements Serializable {
    @EmbeddedId
    private SuburbPostcodeId id;
}
