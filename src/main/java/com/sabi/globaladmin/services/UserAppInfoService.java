package com.sabi.globaladmin.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.UserAppInfoDto;
import com.sabi.globaladmin.dto.responsedto.UserAppInfoResponse;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.UserAppInfo;
import com.sabi.globaladmin.repository.UserAppInfoRepository;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.validations.CoreValidations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class UserAppInfoService {

    private UserAppInfoRepository userAppInfoRepository;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;
    private final CoreValidations validations;

    public UserAppInfoService(UserAppInfoRepository userAppInfoRepository, ModelMapper mapper, ObjectMapper objectMapper,
                              CoreValidations validations) {
        this.userAppInfoRepository = userAppInfoRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.validations = validations;
    }


    public UserAppInfoResponse createAppInfo(UserAppInfoDto request) {
//        validations.validateBank(request);
        UserAppInfo appinfo = mapper.map(request,UserAppInfo.class);
        appinfo.setActionDate(LocalDateTime.now());
        appinfo = userAppInfoRepository.save(appinfo);
        log.debug("Create app info - {}"+ new Gson().toJson(appinfo));
        return mapper.map(appinfo, UserAppInfoResponse.class);
    }


    public UserAppInfoResponse updateAppInfo(UserAppInfoDto request) {
//        validations.validateBank(request);

        UserAppInfo appInfo = userAppInfoRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested  id does not exist!"));
        mapper.map(request, appInfo);

        userAppInfoRepository.save(appInfo);
        log.debug("App info record updated - {}"+ new Gson().toJson(appInfo));
        return mapper.map(appInfo, UserAppInfoResponse.class);
    }



    public UserAppInfoResponse findAppInfo(Long id){
        UserAppInfo appInfo = userAppInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested id does not exist!"));
        return mapper.map(appInfo,UserAppInfoResponse.class);
    }


    public Page<UserAppInfo> findAll(String username,Long userId, String applicationCode, PageRequest pageRequest ){
        Page<UserAppInfo> appInfo = userAppInfoRepository.findAppInfos(username,userId,applicationCode,pageRequest);
        if(appInfo == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return appInfo;
    }


    public List<UserAppInfo> getAll(String username,Long userId, String applicationCode){
        List<UserAppInfo> appInfo = userAppInfoRepository.findAppInfoList(username,userId,applicationCode);
        return appInfo;

    }


    public UserAppInfoResponse findByUserIdAndInfo(Long userId,String applicationCode){
        UserAppInfo appInfo = userAppInfoRepository.findByUserIdAndApplicationCode(userId,applicationCode);
        if(appInfo == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return mapper.map(appInfo,UserAppInfoResponse.class);
    }


    public UserAppInfoResponse findByAuthKey(String authKey){
        UserAppInfo appInfo = userAppInfoRepository.findByAuthKey(authKey);
        if(appInfo == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return mapper.map(appInfo,UserAppInfoResponse.class);
    }

}
