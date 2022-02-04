package com.sabi.globaladmin.dto.requestdto;

import lombok.Data;

@Data
public class ForgetPasswordDto {

    private String email;
    private String phone;
}
