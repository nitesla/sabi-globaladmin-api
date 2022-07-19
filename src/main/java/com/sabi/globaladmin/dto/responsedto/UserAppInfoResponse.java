package com.sabi.globaladmin.dto.responsedto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAppInfoResponse {

    private Long id;
    private String username;
    private Long userId;
    private String applicationCode;
    private LocalDateTime actionDate;
    private String authKey;
    private String authKeyExpirationDate;
    private String token;
    List<UserAppAccessList> permissions;

}
