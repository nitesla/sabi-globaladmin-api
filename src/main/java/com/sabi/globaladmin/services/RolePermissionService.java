package com.sabi.globaladmin.services;

import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.EnableDisEnableDto;
import com.sabi.globaladmin.dto.requestdto.RolePermissionDto;
import com.sabi.globaladmin.dto.responsedto.RolePermissionResponseDto;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.Permission;
import com.sabi.globaladmin.model.RolePermission;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.repository.PermissionRepository;
import com.sabi.globaladmin.repository.RolePermissionRepository;
import com.sabi.globaladmin.repository.RoleRepository;
import com.sabi.globaladmin.utils.AuditTrailFlag;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.utils.Utility;
import com.sabi.globaladmin.validations.CoreValidations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.beans.Transient;
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

    public RolePermissionResponseDto createRolePermission(RolePermissionDto request, HttpServletRequest request1) {
        coreValidations.validateRolePermission(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        RolePermission rolePermission = new RolePermission();
        for (long permission : request.getPermissionIds()) {
            rolePermission.setPermissionId(permission);
            rolePermission.setRoleId(request.getRoleId());
            rolePermission.setCreatedBy(userCurrent.getId());
            rolePermission.setStatus(CustomResponseCode.ACTIVE_USER);
            boolean exists = rolePermissionRepository
                    .existsByRoleIdAndPermissionId(request.getRoleId(), permission);
            if (!exists) {
                rolePermission = rolePermissionRepository.save(rolePermission);
                log.debug("Create new RolePermission - {}" + new Gson().toJson(rolePermission));
            }
        }


        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Create new role permission by :" + userCurrent.getUsername(),
                        AuditTrailFlag.CREATE,
                        " Create new role permission for:" + rolePermission.getId() ,1, Utility.getClientIp(request1));
        return mapper.map(rolePermission, RolePermissionResponseDto.class);
    }


    /**
     * <summary>
     * RolePermission update
     * </summary>
     * <remarks>this method is responsible for updating already existing RolePermission</remarks>
     */

    public RolePermissionResponseDto updateRolePermission(RolePermissionDto request,HttpServletRequest request1) {
//        coreValidations.validateRolePermission(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        rolePermissionRepository.findById(request.getId()).orElseThrow(() ->
                new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested role permission id does not exist!")
        );
        RolePermission rolePermission = new RolePermission();
        for (long permission : request.getPermissionIds()) {
            rolePermission.setPermissionId(permission);
            rolePermission.setId(request.getId());
            rolePermission.setRoleId(request.getRoleId());
            rolePermission.setUpdatedBy(userCurrent.getId());
            rolePermission.setStatus(CustomResponseCode.ACTIVE_USER);
            boolean exists = rolePermissionRepository
                    .existsByRoleIdAndPermissionId(request.getRoleId(), permission);
            if (!exists) {
                rolePermission = rolePermissionRepository.save(rolePermission);
                log.debug("Create new RolePermission - {}" + new Gson().toJson(rolePermission));
            }
        }

        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Update role permission by username:" + userCurrent.getUsername(),
                        AuditTrailFlag.UPDATE,
                        " Update role permission Request for:" + rolePermission.getId(),1, Utility.getClientIp(request1));
        return mapper.map(rolePermission, RolePermissionResponseDto.class);
    }


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
//        log.info(String.valueOf(Arrays.asList(rolePermission.getPermissionId())));
        return mapper.map(rolePermission, RolePermissionResponseDto.class);
    }


    /**
     * <summary>
     * Find all functions
     * </summary>
     * <remarks>this method is responsible for getting all records in pagination</remarks>
     */
    public Page<RolePermission> findAll(Long roleId, int status, PageRequest pageRequest) {
        Page<RolePermission> functions = rolePermissionRepository.findRolePermission(roleId, status, pageRequest);
        if (functions == null) {
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return functions;
    }

    public void enableDisEnableState(EnableDisEnableDto request) {
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        RolePermission creditLevel = rolePermissionRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested creditLevel id does not exist!"));
        creditLevel.setStatus(request.getStatus());
        creditLevel.setUpdatedBy(userCurrent.getId());
        rolePermissionRepository.save(creditLevel);

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

    @Transient
    public void deleteRolePermission(Long roleId){
        RolePermission rolePermission = rolePermissionRepository.findById(roleId).orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, "RolePermission not found"));
        rolePermissionRepository.delete(rolePermission);
    }
}
