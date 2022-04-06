package com.sabi.globaladmin.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
public class UserAppInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private Long userId;
    private String applicationCode;
    @ApiModelProperty(hidden = true)
    private LocalDateTime actionDate = LocalDateTime.now();

    @JsonIgnore
    private String authKey;
    @JsonIgnore
    private String authKeyExpirationDate;
    private String token;
    @JsonIgnore
    private String secreteKey;
    @JsonIgnore
    private String ivKey;









}
