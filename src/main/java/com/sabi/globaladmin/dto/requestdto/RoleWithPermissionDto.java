package com.sabi.globaladmin.dto.requestdto;



import com.sabi.globaladmin.model.RolePermission;
import lombok.Data;

import java.util.List;

@Data
public class RoleWithPermissionDto {

    private String name;
    private String description;
    private List<RolePermission> permissions;
}
