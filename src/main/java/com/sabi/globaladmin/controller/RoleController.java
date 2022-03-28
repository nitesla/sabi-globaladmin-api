package com.sabi.globaladmin.controller;


import com.sabi.globaladmin.dto.requestdto.EnableDisEnableDto;
import com.sabi.globaladmin.dto.requestdto.RoleDto;
import com.sabi.globaladmin.dto.responsedto.RoleResponseDto;
import com.sabi.globaladmin.model.Role;
import com.sabi.globaladmin.services.RoleService;
import com.sabi.globaladmin.utils.Constants;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT+"global/role")
public class RoleController {



    private final RoleService service;

    public RoleController(RoleService service) {
        this.service = service;
    }


    /** <summary>
     * Role creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new Role</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createRole(@Validated @RequestBody RoleDto request, HttpServletRequest request1){
        HttpStatus httpCode ;
        Response resp = new Response();
        RoleResponseDto response = service.createRole(request,request1);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Role update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating role</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateRole(@Validated @RequestBody  RoleDto request,HttpServletRequest request1){
        HttpStatus httpCode ;
        Response resp = new Response();
        RoleResponseDto response = service.updateRole(request,request1);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Update Successful");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Get single record endpoint
     * </summary>
     * <remarks>this endpoint is responsible for getting a single record</remarks>
     */
    @GetMapping("/{id}")
    public ResponseEntity<Response> getRole(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        RoleResponseDto response = service.findRole(id);
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
    public ResponseEntity<Response> getRoles(@RequestParam(value = "name",required = false)String name,
                                             @RequestParam(value = "status",required = false)String status,
                                                       @RequestParam(value = "page") int page,
                                                       @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<Role> response = service.findAll(name,status, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }




    /** <summary>
     * Enable disable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disabling a Product</remarks>
     */

    @PutMapping("/enabledisable")
    public ResponseEntity<Response> enableDisEnable(@Validated @RequestBody EnableDisEnableDto request, HttpServletRequest request1){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.enableDisable(request,request1);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

}
