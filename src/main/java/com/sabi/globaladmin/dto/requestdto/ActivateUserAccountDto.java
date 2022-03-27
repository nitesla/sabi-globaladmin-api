package com.sabi.globaladmin.dto.requestdto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ActivateUserAccountDto {


    private String resetToken;

    private Long updatedBy;
    private String status;
    private LocalDateTime passwordChangedOn;


}
