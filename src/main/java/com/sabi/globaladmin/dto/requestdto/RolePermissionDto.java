package com.sabi.globaladmin.dto.requestdto;

import com.sabi.globaladmin.model.RolePermission;
import lombok.Data;

import java.util.List;

@Data
public class RolePermissionDto {
    private Long id;
    private Long roleId;
    private List<RolePermission> permissionIds;
//    private List<Long> permissionIds;
}
