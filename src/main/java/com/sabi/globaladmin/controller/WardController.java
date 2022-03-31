package com.sabi.globaladmin.controller;


import com.sabi.globaladmin.dto.requestdto.WardDto;
import com.sabi.globaladmin.dto.responsedto.WardResponseDto;
import com.sabi.globaladmin.model.Ward;
import com.sabi.globaladmin.services.WardService;
import com.sabi.globaladmin.utils.Constants;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@SuppressWarnings("All")
@Valid
@RestController
@RequestMapping(Constants.APP_CONTENT +"ward")
public class WardController {

    private final WardService service;

    public WardController(WardService service) {
        this.service = service;
    }

    /** <summary>
     *  Ward creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new Ward</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createWard(@Validated @RequestBody WardDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        WardResponseDto response = service.createWard(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }

    /** <summary>
     * Ward update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating Ward</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateWard(@Validated @RequestBody  WardDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        WardResponseDto response = service.updateWard(request);
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
    public ResponseEntity<Response> getWard(@Validated @PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        WardResponseDto response = service.findWard(id);
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

    @GetMapping("/page")
    public ResponseEntity<Response> getWards(@RequestParam(value = "name",required = false)String name,
                                             @RequestParam(value = "lgaId",required = false)Long lgaId,
                                              @RequestParam(value = "page") int page,
                                              @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<Ward> response = service.findAll(name, lgaId, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "name") String name,
                                           @RequestParam(value = "lgaId") Long lgaId){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Ward> response = service.getAll(name,lgaId);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

}
