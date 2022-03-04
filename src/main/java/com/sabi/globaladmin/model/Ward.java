package com.sabi.globaladmin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.*;

/**
 *
 * This class is responsible for persisting to the database
 */

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Ward {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Long lgaId;

    @Transient
    private String LgaName;
}
