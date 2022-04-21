package com.sabi.globaladmin.controller;


import com.sabi.globaladmin.dto.requestdto.AuthKeyRequest;
import com.sabi.globaladmin.dto.requestdto.UserAppInfoDto;
import com.sabi.globaladmin.dto.responsedto.UserAppInfoResponse;
import com.sabi.globaladmin.dto.responsedto.UserInforResponse;
import com.sabi.globaladmin.model.UserAppInfo;
import com.sabi.globaladmin.services.UserAppInfoService;
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
@RequestMapping(Constants.APP_CONTENT +"appinfo")
public class UserAppInfoController {

    private final UserAppInfoService service;
    private final WhatsAppService whatsAppService;

    public UserAppInfoController(UserAppInfoService service, WhatsAppService whatsAppService) {
        this.service = service;
        this.whatsAppService = whatsAppService;
    }


    @PostMapping("")
    public ResponseEntity<Response> createAppInfo(@Validated @RequestBody UserAppInfoDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        UserAppInfoResponse response = service.createAppInfo(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



//    @PutMapping("")
//    public ResponseEntity<Response> updateAppCode(@Validated @RequestBody  UserAppInfoDto request){
//        HttpStatus httpCode ;
//        Response resp = new Response();
//        UserAppInfoResponse response = service.updateAppInfo(request);
//        resp.setCode(CustomResponseCode.SUCCESS);
//        resp.setDescription("Update Successful");
//        resp.setData(response);
//        httpCode = HttpStatus.OK;
//        return new ResponseEntity<>(resp, httpCode);
//    }

    @GetMapping("/{id}")
    public ResponseEntity<Response> getAppCode(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        UserInforResponse response = service.findAppInfo(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    @GetMapping("")
    public ResponseEntity<Response> getByUserIdAndInfo(@RequestParam(value = "userId")Long userId,
                                                       @RequestParam(value = "applicationCode")String applicationCode){
        HttpStatus httpCode ;
        Response resp = new Response();
        UserInforResponse response = service.findByUserIdAndInfo(userId,applicationCode);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/authkey")
    public ResponseEntity<Response> getByAuthKey(AuthKeyRequest authKey){
        HttpStatus httpCode ;
        Response resp = new Response();
        UserInforResponse response = service.findByAuthKey(authKey);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }





    @GetMapping("/page")
    public ResponseEntity<Response> getAppInfos(@RequestParam(value = "username",required = false)String username,
                                                @RequestParam(value = "userId",required = false)Long userId,
                                                @RequestParam(value = "applicationCode",required = false)String applicationCode,
                                                @RequestParam(value = "page") int page,
                                                @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<UserAppInfo> response = service.findAll(username,userId,applicationCode, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "username",required = false)String username,
                                           @RequestParam(value = "userId",required = false)Long userId,
                                           @RequestParam(value = "applicationCode",required = false)String applicationCode){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<UserAppInfo> response = service.getAll(username,userId,applicationCode);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
