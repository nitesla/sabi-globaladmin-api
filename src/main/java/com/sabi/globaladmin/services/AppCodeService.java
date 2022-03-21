package com.sabi.globaladmin.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.AppCodeDto;
import com.sabi.globaladmin.dto.responsedto.AppCodeResponseDto;
import com.sabi.globaladmin.exceptions.ConflictException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.AppCodes;
import com.sabi.globaladmin.repository.AppCodesRepository;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.validations.CoreValidations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class AppCodeService {
    private AppCodesRepository appCodesRepository;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;
    private final CoreValidations validations;

    public AppCodeService(AppCodesRepository appCodesRepository, ModelMapper mapper, ObjectMapper objectMapper, CoreValidations validations) {
        this.appCodesRepository = appCodesRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.validations = validations;
    }


    public AppCodeResponseDto createAppCode(AppCodeDto request) {
//        validations.validateBank(request);
        AppCodes appCodes = mapper.map(request,AppCodes.class);
        AppCodes appCodeExist = appCodesRepository.findByAppCode(request.getAppCode());
        if(appCodeExist !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " App code already exist");
        }
        appCodes = appCodesRepository.save(appCodes);
        log.debug("Create app code - {}"+ new Gson().toJson(appCodes));
        return mapper.map(appCodes, AppCodeResponseDto.class);
    }



    public AppCodeResponseDto updateAppCode(AppCodeDto request) {
//        validations.validateBank(request);

        AppCodes appCodes = appCodesRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested  id does not exist!"));
        mapper.map(request, appCodes);

        appCodesRepository.save(appCodes);
        log.debug("App code record updated - {}"+ new Gson().toJson(appCodes));
        return mapper.map(appCodes, AppCodeResponseDto.class);
    }



    public AppCodeResponseDto findAppCode(Long id){
        AppCodes appCodes = appCodesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested id does not exist!"));
        return mapper.map(appCodes,AppCodeResponseDto.class);
    }


    public Page<AppCodes> findAll(String appCode, String appName, PageRequest pageRequest ){
        Page<AppCodes> appCodes = appCodesRepository.findAppCodes(appCode,appName,pageRequest);
        if(appCodes == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return appCodes;
    }


    public List<AppCodes> getAll(String appCode, String appName){
        List<AppCodes> appCodes = appCodesRepository.findAppList(appCode,appName);
        return appCodes;

    }
}
