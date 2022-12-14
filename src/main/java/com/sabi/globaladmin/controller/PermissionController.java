package com.sabi.globaladmin.controller;


import com.sabi.globaladmin.dto.requestdto.PermissionDto;
import com.sabi.globaladmin.dto.responsedto.PermissionResponseDto;
import com.sabi.globaladmin.model.Permission;
import com.sabi.globaladmin.services.PermissionService;
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
import java.util.List;

@RestController
@RequestMapping(Constants.APP_CONTENT+"permission")
public class PermissionController {



    private final PermissionService service;

    public PermissionController(PermissionService service) {
        this.service = service;
    }


    /** <summary>
     * Permission creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new permission</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createPermission(@Validated @RequestBody PermissionDto request, HttpServletRequest request1){
        HttpStatus httpCode ;
        Response resp = new Response();
        PermissionResponseDto response = service.createPermission(request,request1);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }


    /** <summary>
     * Permission update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating permission</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updatePermission(@Validated @RequestBody  PermissionDto request,HttpServletRequest request1){
        HttpStatus httpCode ;
        Response resp = new Response();
        PermissionResponseDto response = service.updatePermission(request,request1);
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
    public ResponseEntity<Response> getPermission(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        PermissionResponseDto response = service.findPermission(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    @GetMapping("/permname")
    public PermissionResponseDto getPermissionByName(String name){
        PermissionResponseDto response = service.findPermissionByName(name);
        return response;
    }


    /** <summary>
     * Get all records endpoint
     * </summary>
     * <remarks>this endpoint is responsible for getting all records and its searchable</remarks>
     */
    @GetMapping("/page")
    public ResponseEntity<Response> getPermissions(@RequestParam(value = "name",required = false)String name,
                                                   @RequestParam(value = "appPermission",required = false)String appPermission,
                                                   @RequestParam(value = "page") int page,
                                                   @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<Permission> response = service.findAll(name,appPermission, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "name",required = false)String name,
                                           @RequestParam(value = "appPermission")String appPermission){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Permission> response = service.getAll(name,appPermission);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

}
