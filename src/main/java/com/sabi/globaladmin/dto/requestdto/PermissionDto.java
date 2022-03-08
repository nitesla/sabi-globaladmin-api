package com.sabi.globaladmin.dto.requestdto;


import lombok.Data;

@Data
public class PermissionDto {

    private Long id;
    private String name;
    private String menuName;
    private String url;
    private String permissionType;
    private String appPermission;


}
