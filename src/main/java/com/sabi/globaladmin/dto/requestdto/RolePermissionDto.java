package com.sabi.globaladmin.dto.requestdto;

import lombok.Data;

import java.util.List;

@Data
public class RolePermissionDto {
    private Long id;
    private Long roleId;
    private List<Long> permissionIds;
}
