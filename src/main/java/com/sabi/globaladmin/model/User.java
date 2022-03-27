package com.sabi.globaladmin.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Entity
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {



    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private int loginAttempts;
    private LocalDateTime failedLoginDate;
    private LocalDateTime lastLogin;
    private String password;
    private String passwordExpiration;
    private Date lockedDate;
    private String firstName;
    private String lastName;
    private String middleName;
    private String username;
    private Long roleId;
    @Transient
    private String roleName;
    private LocalDateTime passwordChangedOn;
    @Transient
    private boolean loginStatus;
    private String email;
    private String phone;
    private String userCategory;
    private String resetToken;
    private String resetTokenExpirationDate;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createdDate = LocalDateTime.now();

    @UpdateTimestamp
    @ApiModelProperty(hidden = true)
    private LocalDateTime updatedDate = LocalDateTime.now();

    private Long createdBy;
    private Long updatedBy;

    private String status;
}
