package com.sabi.globaladmin.controller;



import com.sabi.globaladmin.dto.requestdto.StateDto;
import com.sabi.globaladmin.dto.responsedto.StateResponseDto;
import com.sabi.globaladmin.model.State;
import com.sabi.globaladmin.services.StateService;
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
@RequestMapping(Constants.APP_CONTENT+"state")
public class StateController {


    private final StateService service;

    public StateController(StateService service) {
        this.service = service;
    }


    /** <summary>
     * State creation endpoint
     * </summary>
     * <remarks>this endpoint is responsible for creation of new states</remarks>
     */

    @PostMapping("")
    // @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CREATE_USER')")
    public ResponseEntity<Response> createState(@Validated @RequestBody StateDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        StateResponseDto response = service.createState(request);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Successful");
        resp.setData(response);
        httpCode = HttpStatus.CREATED;
        return new ResponseEntity<>(resp, httpCode);
    }



    /** <summary>
     * State update endpoint
     * </summary>
     * <remarks>this endpoint is responsible for updating states</remarks>
     */

    @PutMapping("")
    // @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CREATE_USER')")
    public ResponseEntity<Response> updateState(@Validated @RequestBody  StateDto request){
        HttpStatus httpCode ;
        Response resp = new Response();
        StateResponseDto response = service.updateState(request);
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
    // @PreAuthorize("hasAnyRole('ROLE_SUPER_ADMIN','ROLE_CREATE_USER')")
    public ResponseEntity<Response> getState(@PathVariable Long id){
        HttpStatus httpCode ;
        Response resp = new Response();
        StateResponseDto response = service.findState(id);
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
    public ResponseEntity<Response> getStates(@RequestParam(value = "name",required = false)String name,
                                              @RequestParam(value = "countryId",required = false)Long countryId,
                                             @RequestParam(value = "page") int page,
                                             @RequestParam(value = "pageSize") int pageSize){
        HttpStatus httpCode ;
        Response resp = new Response();
        Page<State> response = service.findAll(name,countryId,PageRequest.of(page, pageSize));
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
    public ResponseEntity<Response> getAll(@RequestParam(value = "countryId",required = false)Long countryId,
                                           @RequestParam(value = "name",required = false)String name){
        HttpStatus httpCode ;
        Response resp = new Response();
        List<State> response = service.getAllByCountryId(countryId,name);
        resp.setCode(CustomResponseCode.SUCCESS);
        resp.setDescription("Record fetched successfully !");
        resp.setData(response);
        httpCode = HttpStatus.OK;
        return new ResponseEntity<>(resp, httpCode);
    }



}
