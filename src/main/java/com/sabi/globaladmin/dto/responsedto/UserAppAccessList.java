package com.sabi.globaladmin.dto.responsedto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAppAccessList {

    private String name;
    private String menuName;
    private String appPermission;
}
