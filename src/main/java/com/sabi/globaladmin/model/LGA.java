package com.sabi.globaladmin.model;



import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 *
 * This class is responsible for persisting to the database
 */


@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Data
@Entity
public class LGA  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long stateId;
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdDate = LocalDateTime.now();

    @UpdateTimestamp
    @ApiModelProperty(hidden = true)
    private LocalDateTime updatedDate = LocalDateTime.now();

    private Long createdBy;
    private Long updatedBy;


    public LGA(String name, Long stateId) {
        this.name = name;
        this.stateId = stateId;
    }
    @Transient
    private String stateName;

}
