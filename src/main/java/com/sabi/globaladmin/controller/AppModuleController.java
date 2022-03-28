package com.sabi.globaladmin.controller;


import com.sabi.globaladmin.dto.requestdto.ApplicationModuleDto;
import com.sabi.globaladmin.dto.requestdto.EnableDisEnableDto;
import com.sabi.globaladmin.dto.responsedto.ApplicationModuleResponse;
import com.sabi.globaladmin.model.ApplicationModule;
import com.sabi.globaladmin.services.ApplicationModuleService;
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

@RestController
@RequestMapping(Constants.APP_CONTENT +"global/appmodule")
public class AppModuleController {

    private final ApplicationModuleService service;
    private final WhatsAppService whatsAppService;

    public AppModuleController(ApplicationModuleService service, WhatsAppService whatsAppService) {
        this.service = service;
        this.whatsAppService = whatsAppService;
    }



    @PostMapping("")
    public ResponseEntity<Response> createAppModule(@Validated @RequestBody ApplicationModuleDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        ApplicationModuleResponse response = service.createAppModule(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    @PutMapping("")
    public ResponseEntity<Response> updateAppModule(@Validated @RequestBody  ApplicationModuleDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        ApplicationModuleResponse response = service.updateAppModule(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Update Successful");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/{id}")
    public ResponseEntity<Response> getAppModule(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        ApplicationModuleResponse response = service.findAppModule(id);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }


    @GetMapping("/page")
    public ResponseEntity<Response> getAppModules(@RequestParam(value = "appCode",required = false)String appCode,
                                                @RequestParam(value = "name",required = false)String name,
                                                  @RequestParam(value = "status",required = false) String status,
                                                @RequestParam(value = "page") int page,
                                                @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<ApplicationModule> response = service.findAll(appCode,name,status, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "appCode",required = false)String appCode,
                                           @RequestParam(value = "name",required = false)String name,
                                           @RequestParam(value = "status",required = false) String status){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<ApplicationModule> response = service.getAll(appCode,name,status);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



    @PutMapping("/enabledisable")
    public ResponseEntity<Response> enableDisable(@Validated @RequestBody EnableDisEnableDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        service.enableDisable(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }

}
