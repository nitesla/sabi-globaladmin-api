package com.sabi.globaladmin.controller;


import com.sabi.globaladmin.dto.requestdto.AppCodeDto;
import com.sabi.globaladmin.dto.responsedto.AppCodeResponseDto;
import com.sabi.globaladmin.model.AppCodes;
import com.sabi.globaladmin.services.AppCodeService;
import com.sabi.globaladmin.services.WhatsAppService;
import com.sabi.globaladmin.utils.Constants;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.utils.Response;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT +"global/appcode")
public class AppCodeController {

    private final AppCodeService service;
    private final WhatsAppService whatsAppService;

    public AppCodeController(AppCodeService service, WhatsAppService whatsAppService) {
        this.service = service;
        this.whatsAppService = whatsAppService;
    }



    @PostMapping("")
    public ResponseEntity<Response> createAppCode(@Validated @RequestBody AppCodeDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        AppCodeResponseDto response = service.createAppCode(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    @PutMapping("")
    public ResponseEntity<Response> updateAppCode(@Validated @RequestBody  AppCodeDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        AppCodeResponseDto response = service.updateAppCode(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Update Successful");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getAppCode(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        AppCodeResponseDto response = service.findAppCode(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/page")
    public ResponseEntity<Response> getAppCodes(@RequestParam(value = "appCode",required = false)String appCode,
                                             @RequestParam(value = "appName",required = false)String appName,
                                             @RequestParam(value = "page") int page,
                                             @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<AppCodes> response = service.findAll(appCode,appName, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "appCode",required = false)String appCode,
                                           @RequestParam(value = "appName",required = false)String appName){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<AppCodes> response = service.getAll(appCode,appName);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
