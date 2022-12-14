package com.sabi.globaladmin.controller;



import com.sabi.globaladmin.dto.requestdto.LGADto;
import com.sabi.globaladmin.dto.responsedto.LGAResponseDto;
import com.sabi.globaladmin.model.LGA;
import com.sabi.globaladmin.services.LGAService;
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
@RequestMapping(Constants.APP_CONTENT+"lga")
public class LGAController {


    private final LGAService service;

    public LGAController(LGAService service) {
        this.service = service;
    }

    /** <summary>
     * LGA creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new lga</remarks>
     */

    @PostMapping("")
    public ResponseEntity<Response> createLga(@Validated @RequestBody LGADto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        LGAResponseDto response = service.createLga(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * LGA update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating lga</remarks>
     */

    @PutMapping("")
    public ResponseEntity<Response> updateLga(@Validated @RequestBody  LGADto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        LGAResponseDto response = service.updateLga(request);
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
    public ResponseEntity<Response> getLga(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        LGAResponseDto response = service.findLga(id);
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
    public ResponseEntity<Response> getLgas(@RequestParam(value = "name",required = false)String name,
                                            @RequestParam(value = "stateId",required = false)Long stateId,
                                              @RequestParam(value = "page") int page,
                                              @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<LGA> response = service.findAll(name,stateId, PageRequest.of(page, pageSize));
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * Enable disenable
     * </summary>
     * <remarks>this endpoint is responsible for enabling and disenabling a State</remarks>
     */

//    @PutMapping("/enabledisenable")
//    public ResponseEntity<Response> enableDisEnable(@Validated @RequestBody EnableDisEnableDto request){
//        HttpStatus httpCode ;
//        Response resp = new Response();
//        service.enableDisEnableState(request);
//        resp.setCode(CustomResponseCode.SUCCESS);
//        resp.setDescription("Successful");
//        httpCode = HttpStatus.OK;
//        return new ResponseEntity<>(resp, httpCode);
//    }


    @GetMapping("/list")
    public ResponseEntity<Response> getAll(@RequestParam(value = "stateId",required = false)Long stateId,
                                           @RequestParam(value = "name",required = false)String name){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<LGA> response = service.getAllByStateId(stateId,name);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



}
