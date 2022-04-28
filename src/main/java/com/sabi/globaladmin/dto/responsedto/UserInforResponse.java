package com.sabi.globaladmin.dto.responsedto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserInforResponse {

    private Long id;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
    private Long userId;
    private String applicationCode;
    private LocalDateTime actionDate;
    private String token;
    private String authKeyExpirationDate;
}
