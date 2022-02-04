package com.sabi.globaladmin.dto.requestdto;

import lombok.Data;

@Data
public class ChangePasswordDto {

    private Long id;
    private String password;
    private String previousPassword;
}
