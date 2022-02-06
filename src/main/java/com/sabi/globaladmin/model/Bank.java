package com.sabi.globaladmin.model;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@Entity
public class Bank {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String code;
    @ApiModelProperty(hidden = true)
    private LocalDateTime createdDate = LocalDateTime.now();

    @UpdateTimestamp
    @ApiModelProperty(hidden = true)
    private LocalDateTime updatedDate = LocalDateTime.now();

    private Long createdBy;
    private Long updatedBy;
    private int status;

    public Bank(String name, String code) {
        this.name = name;
        this.code = code;
    }
}
