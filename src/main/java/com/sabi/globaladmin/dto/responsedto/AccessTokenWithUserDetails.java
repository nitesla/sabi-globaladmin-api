package com.sabi.globaladmin.dto.responsedto;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sabi.globaladmin.model.User;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class AccessTokenWithUserDetails implements Serializable{


    @JsonProperty("accessToken")
    private String accessToken;

    @JsonProperty("phone")
    private String phone;

    @JsonProperty("email")
    private String email;

    @JsonProperty("menu")
    private String menu;


    @JsonProperty("lastName")
    private String lastName;

    @JsonProperty("firstName")
    private String firstName;


    @JsonProperty("lastLogin")
    private LocalDateTime lastLogin;



    @JsonProperty("tokenExpiry")
    private long tokenExpiry;

    @JsonProperty("userId")
    private long userId;

    @JsonProperty("permissions")
    List<AccessListDto> permissions;





    public AccessTokenWithUserDetails(String token, User user, String menu, long tokenExpiry,List<AccessListDto> permissions) {
        this.accessToken = token;

        this.email = user.getEmail();
        this.phone = user.getPhone();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.lastLogin = user.getLastLogin();
        this.menu = menu;
        this.tokenExpiry = tokenExpiry;
        this.userId=user.getId();
        this.permissions=permissions;









    }

}
