package com.sabi.globaladmin.dto.requestdto;


import lombok.Data;

@Data
public class AuthKeyRequest {

    private String authKey;
    private Long userId;
    private String applicationCode;
}
