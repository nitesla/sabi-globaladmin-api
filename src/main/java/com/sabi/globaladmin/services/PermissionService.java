package com.sabi.globaladmin.services;


import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.PermissionDto;
import com.sabi.globaladmin.dto.responsedto.AccessListDto;
import com.sabi.globaladmin.dto.responsedto.AppPermissionResponse;
import com.sabi.globaladmin.dto.responsedto.PermissionResponseDto;
import com.sabi.globaladmin.exceptions.ConflictException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.AppCodes;
import com.sabi.globaladmin.model.Permission;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.repository.AppCodesRepository;
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
    private AppCodesRepository appCodesRepository;
    private PermissionRepository permissionRepository;
    private final ModelMapper mapper;
    private final CoreValidations coreValidations;
    private final AuditTrailService auditTrailService;



    public PermissionService(AppCodesRepository appCodesRepository,PermissionRepository permissionRepository, ModelMapper mapper, CoreValidations coreValidations,
                             AuditTrailService auditTrailService) {
        this.appCodesRepository = appCodesRepository;
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

        AppCodes appCodeExist = appCodesRepository.findByAppCode(request.getAppPermission());
        if(appCodeExist == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,"App code does not exist");
        }
        Permission permissionExist = permissionRepository.findByNameAndAppPermission(request.getName(),request.getAppPermission());
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
        AppCodes appCodeExist = appCodesRepository.findByAppCode(request.getAppPermission());
        if(appCodeExist == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,"App code does not exist");
        }
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


    public PermissionResponseDto findPermissionByName(String name){
        Permission permission = permissionRepository.findByName(name);
        return mapper.map(permission,PermissionResponseDto.class);
    }



    /** <summary>
     * Find all functions
     * </summary>
     * <remarks>this method is responsible for getting all records in pagination</remarks>
     */
    public Page<Permission> findAll(String name,String appPermission, PageRequest pageRequest ){
        Page<Permission> functions = permissionRepository.findFunctions(name,CustomResponseCode.ACTIVE_USER,appPermission,pageRequest);
        if(functions == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return functions;

    }


    public List<Permission> getAll(String name,String appPermission){
        List<Permission> permissions = permissionRepository.listPermission(name,CustomResponseCode.ACTIVE_USER,appPermission);
        return permissions;

    }




    public List<AccessListDto> getPermissionsByUserId(Long userId) {

        List<AccessListDto> resultLists = new ArrayList<>();
        List<Object[]> result = permissionRepository.getPermissionsByUserId(userId);
        try {
            result.forEach(r -> {
                AccessListDto userPermission = new AccessListDto();
                userPermission.setName((String) r[0]);
//                userPermission.setAppPermission((String) r[1]);
                resultLists.add(userPermission);

            });
        } catch (Exception var5) {
            log.info("Error in returning object list" + var5);
        }
        return resultLists;

    }



    public String getPermissionsGrouping(Long userId) {

        List<AppPermissionResponse> resultLists = new ArrayList<>();
        List<Object[]> result = permissionRepository.getPermissionsGrouping(userId);

        result.forEach(r -> {
            AppPermissionResponse userPermission = new AppPermissionResponse();
            userPermission.setAppPermission((String) r[0]);
            resultLists.add(userPermission);

        });

        String accessList = StringUtils.join(resultLists, ',');

        String access = accessList.replace("AppPermissionResponse","").replaceAll("[()]","")
                .replace("name","").replace("=","").replace("appPermission","");
        return access;

    }

}
