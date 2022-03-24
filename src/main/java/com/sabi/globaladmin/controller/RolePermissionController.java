package com.sabi.globaladmin.controller;


import com.sabi.globaladmin.dto.requestdto.RolePermissionDto;
import com.sabi.globaladmin.dto.responsedto.RolePermissionResponseDto;
import com.sabi.globaladmin.model.RolePermission;
import com.sabi.globaladmin.services.RolePermissionService;
import com.sabi.globaladmin.utils.Constants;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.utils.Response;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(Constants.APP_CONTENT+"global/rolepermission")
public class RolePermissionController {
    private final RolePermissionService service;

    public RolePermissionController(RolePermissionService service) {
        this.service = service;
    }


    /** <summary>
     * RolePermission creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new RolePermission</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> assignPermission(@Validated @RequestBody RolePermissionDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.assignPermission(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * RolePermission update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating RolePermission</remarks>
     */




    /** <summary>
     * Get single record endpoint
     * </summary>
     * <remarks>this endpoint is responsible for getting a single record</remarks>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response> getRolePermission(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        RolePermissionResponseDto response = service.findRolePermission(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Get all records endpoint
     * </summary>
     * <remarks>this endpoint is responsible for getting all records and its searchable</remarks>
     */
    @GetMapping("")
    public ResponseEntity<Response> getRolePermissions(@RequestParam(value = "roleId",required = false)Long roleId,
                                             @RequestParam(value = "status",required = false)int status,
                                             @RequestParam(value = "page") int page,
                                             @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<RolePermission> response = service.findAll(roleId,status, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }




    @DeleteMapping("/{id}")
    public ResponseEntity<Response> removePermission(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.removePermission(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Permission removed successfully !");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

}
