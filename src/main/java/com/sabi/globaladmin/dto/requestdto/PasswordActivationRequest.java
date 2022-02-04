package com.sabi.globaladmin.dto.requestdto;


import lombok.Data;

@Data
public class PasswordActivationRequest {

    private Long id;
    private String password;
}
