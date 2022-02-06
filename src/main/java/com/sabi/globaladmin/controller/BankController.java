package com.sabi.globaladmin.controller;


import com.sabi.globaladmin.dto.requestdto.BankDto;
import com.sabi.globaladmin.dto.requestdto.EnableDisEnableDto;
import com.sabi.globaladmin.dto.responsedto.BankResponseDto;
import com.sabi.globaladmin.model.Bank;
import com.sabi.globaladmin.services.BankService;
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
@RequestMapping(Constants.APP_CONTENT +"global/bank")
public class BankController {


    private final BankService service;
    private final WhatsAppService whatsAppService;

    public BankController(BankService service, WhatsAppService whatsAppService) {
        this.service = service;
        this.whatsAppService = whatsAppService;
    }


    /** <summary>
     * Bank creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new bank</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createBank(@Validated @RequestBody BankDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        BankResponseDto response = service.createBank(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }





    /** <summary>
     * Bank update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating bank</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateBank(@Validated @RequestBody  BankDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        BankResponseDto response = service.updateBank(request);
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
    public ResponseEntity<Response> getBank(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        BankResponseDto response = service.findBank(id);
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
    public ResponseEntity<Response> getBanks(@RequestParam(value = "name",required = false)String name,
                                              @RequestParam(value = "code",required = false)String code,
                                              @RequestParam(value = "page") int page,
                                              @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<Bank> response = service.findAll(name,code, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Enable disenable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disenabling a bank</remarks>
     */

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



    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "status")int status){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<Bank> response = service.getAll(status);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }
}
