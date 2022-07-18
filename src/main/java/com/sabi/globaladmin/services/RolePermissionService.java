package com.sabi.globaladmin.services;

import com.sabi.globaladmin.dto.requestdto.RolePermissionDto;
import com.sabi.globaladmin.dto.responsedto.RolePermissionResponseDto;
import com.sabi.globaladmin.exceptions.ConflictException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.Permission;
import com.sabi.globaladmin.model.Role;
import com.sabi.globaladmin.model.RolePermission;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.repository.PermissionRepository;
import com.sabi.globaladmin.repository.RolePermissionRepository;
import com.sabi.globaladmin.repository.RoleRepository;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.validations.CoreValidations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class RolePermissionService {

    private final RolePermissionRepository rolePermissionRepository;
    private final ModelMapper mapper;
    private final CoreValidations coreValidations;
    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;
    private final AuditTrailService auditTrailService;


    public RolePermissionService(RolePermissionRepository RolePermissionRepository,
                                 ModelMapper mapper, CoreValidations coreValidations,
                                 PermissionRepository permissionRepository,RoleRepository roleRepository,
                                 AuditTrailService auditTrailService) {
        this.rolePermissionRepository = RolePermissionRepository;
        this.mapper = mapper;
        this.coreValidations = coreValidations;
        this.permissionRepository = permissionRepository;
        this.roleRepository = roleRepository;
        this.auditTrailService = auditTrailService;
    }

    /**
     * <summary>
     * RolePermission creation
     * </summary>
     * <remarks>this method is responsible for creation of new RolePermission</remarks>
     */

    public void assignPermission(RolePermissionDto request) {
        coreValidations.validateRolePermission(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        List<RolePermission> rolePerm = new ArrayList<>();
        request.getPermissionIds().forEach(p -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setPermissionId(p.getPermissionId());
            rolePermission.setRoleId(request.getRoleId());
            rolePermission.setCreatedBy(userCurrent.getId());
            rolePermission.setStatus(1);
            log.info(" role permission details " + rolePermission);
            RolePermission exist = rolePermissionRepository.findByRoleIdAndPermissionId(request.getRoleId(),p.getPermissionId());
            if(exist != null){
                throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Permission id already assigned to the role ::::"+p.getPermissionId());
            }

            rolePermissionRepository.save(rolePermission);
            rolePerm.add(rolePermission);

        });
    }



    /**
     * <summary>
     * RolePermission update
     * </summary>
     * <remarks>this method is responsible for updating already existing RolePermission</remarks>
     */




    /**
     * <summary>
     * Find RolePermission
     * </summary>
     * <remarks>this method is responsible for getting a single record</remarks>
     */
    public RolePermissionResponseDto findRolePermission(Long id) {
        RolePermission rolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested RolePermission id does not exist!"));
        Role role = roleRepository.getOne(rolePermission.getRoleId());
        Permission permission = permissionRepository.getOne(rolePermission.getPermissionId());

        RolePermissionResponseDto response = RolePermissionResponseDto.builder()
                .permissionName(permission.getName())
                .permissionId(rolePermission.getPermissionId())
                .roleId(rolePermission.getRoleId())
                .roleName(role.getName())
                .createdBy(rolePermission.getCreatedBy())
                .createdDate(rolePermission.getCreatedDate())
                .updatedBy(rolePermission.getUpdatedBy())
                .updatedDate(rolePermission.getUpdatedDate())
                .build();
        return response;
    }


    /**
     * <summary>
     * Find all functions
     * </summary>
     * <remarks>this method is responsible for getting all records in pagination</remarks>
     */
    public Page<RolePermission> findAll(Long roleId, PageRequest pageRequest) {
        Page<RolePermission> functions = rolePermissionRepository.findRolePermission(roleId, pageRequest);
        if (functions == null) {
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }

        functions.getContent().forEach(rolePerm-> {
            Role role = roleRepository.getOne(rolePerm.getRoleId());
            rolePerm.setRoleName(role.getName());
            Permission permission = permissionRepository.getOne(rolePerm.getPermissionId());
            rolePerm.setPermission(permission.getName());
        });
        return functions;
    }








    public List<RolePermission> getPermissionsByRole(Long roleId) {
        List<RolePermission> permissionRole = rolePermissionRepository.getPermissionsByRole(roleId);
        for (RolePermission permRole : permissionRole
                ) {

            Permission permission = permissionRepository.getOne(permRole.getPermissionId());
            permRole.setPermission(permission.getName());
        }
        return permissionRole;
    }




    public void removePermission(Long id){
        RolePermission rolePermission = rolePermissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested RolePermission id does not exist!"));
        rolePermissionRepository.delete(rolePermission);
    }





    public void deleteAllBYIds(List<Long> Longs) {
        rolePermissionRepository.deleteByIdIn(Longs);
    }

}
