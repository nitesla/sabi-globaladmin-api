package com.sabi.globaladmin.dto.requestdto;


import lombok.Data;

@Data
public class UserAppInfoDto {

    private Long id;
//    private String username;
//    private Long userId;
    private String applicationCode;
//    private String authKey;
    private String token;
}
