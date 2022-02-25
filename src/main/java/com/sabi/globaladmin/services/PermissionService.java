package com.sabi.globaladmin.services;


import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.PermissionDto;
import com.sabi.globaladmin.dto.responsedto.AccessListDto;
import com.sabi.globaladmin.dto.responsedto.PermissionResponseDto;
import com.sabi.globaladmin.exceptions.ConflictException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.Permission;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.repository.PermissionRepository;
import com.sabi.globaladmin.repository.UserRoleRepository;
import com.sabi.globaladmin.utils.AuditTrailFlag;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.utils.Utility;
import com.sabi.globaladmin.validations.CoreValidations;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@Service
public class PermissionService {

    @Autowired
    private UserRoleRepository userRoleRepository;

    private PermissionRepository permissionRepository;
    private final ModelMapper mapper;
    private final CoreValidations coreValidations;
    private final AuditTrailService auditTrailService;



    public PermissionService(PermissionRepository permissionRepository, ModelMapper mapper, CoreValidations coreValidations,
                             AuditTrailService auditTrailService) {
        this.permissionRepository = permissionRepository;
        this.mapper = mapper;
        this.coreValidations = coreValidations;
        this.auditTrailService = auditTrailService;
    }


    /** <summary>
     * Permission creation
     * </summary>
     * <remarks>this method is responsible for creation of new permission</remarks>
     */

    public PermissionResponseDto createPermission(PermissionDto request, HttpServletRequest request1) {
        coreValidations.validatePermission(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        Permission permission = mapper.map(request,Permission.class);
        Permission permissionExist = permissionRepository.findByName(request.getName());
        if(permissionExist !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Permission already exist");
        }
        permission.setCreatedBy(userCurrent.getId());
        permission.setStatus(CustomResponseCode.ACTIVE_USER);
        permission = permissionRepository.save(permission);
        log.debug("Create new permission - {}"+ new Gson().toJson(permission));

        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Create new permission by :" + userCurrent.getUsername(),
                        AuditTrailFlag.CREATE,
                        " Create new permission for:" + permission.getName(),1, Utility.getClientIp(request1));
        return mapper.map(permission, PermissionResponseDto.class);
    }




    /** <summary>
     * Permission update
     * </summary>
     * <remarks>this method is responsible for updating already existing permission</remarks>
     */

    public PermissionResponseDto updatePermission(PermissionDto request,HttpServletRequest request1) {
//        coreValidations.validateFunction(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        Permission permission = permissionRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested permission id does not exist!"));
        mapper.map(request, permission);
        permission.setUpdatedBy(userCurrent.getId());
        permissionRepository.save(permission);
        log.debug("permission record updated - {}"+ new Gson().toJson(permission));

        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Update permission by username:" + userCurrent.getUsername(),
                        AuditTrailFlag.UPDATE,
                        " Update permission Request for:" + permission.getId(),1, Utility.getClientIp(request1));
        return mapper.map(permission, PermissionResponseDto.class);
    }




    /** <summary>
     * Find permission
     * </summary>
     * <remarks>this method is responsible for getting a single record</remarks>
     */
    public PermissionResponseDto findPermission(Long id){
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested permission id does not exist!"));
        return mapper.map(permission,PermissionResponseDto.class);
    }




    /** <summary>
     * Find all functions
     * </summary>
     * <remarks>this method is responsible for getting all records in pagination</remarks>
     */
    public Page<Permission> findAll(String name,int status, PageRequest pageRequest ){
        Page<Permission> functions = permissionRepository.findFunctions(name,status,pageRequest);
        if(functions == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return functions;

    }






    public String getPermissionsByUserId(Long userId) {

        List<AccessListDto> resultLists = new ArrayList<>();
        List<Object[]> result = permissionRepository.getPermissionsByUserId(userId);

            result.forEach(r -> {
                AccessListDto userPermission = new AccessListDto();
                userPermission.setName((String) r[0]);
                resultLists.add(userPermission);

            });

         String accessList = StringUtils.join(resultLists, ',');

        String access = accessList.replace("AccessListDto","").replaceAll("[()]","")
                .replace("name","").replace("=","");
        return access;

    }

}