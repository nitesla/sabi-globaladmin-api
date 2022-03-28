package com.sabi.globaladmin.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.ApplicationModuleDto;
import com.sabi.globaladmin.dto.requestdto.EnableDisEnableDto;
import com.sabi.globaladmin.dto.responsedto.ApplicationModuleResponse;
import com.sabi.globaladmin.exceptions.ConflictException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.AppCodes;
import com.sabi.globaladmin.model.ApplicationModule;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.repository.AppCodesRepository;
import com.sabi.globaladmin.repository.ApplicationModelRepository;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.validations.CoreValidations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


@SuppressWarnings("ALL")
@Service
@Slf4j
public class ApplicationModuleService {

    private AppCodesRepository appCodesRepository;
    private ApplicationModelRepository applicationModelRepository;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;
    private final CoreValidations validations;

    public ApplicationModuleService(AppCodesRepository appCodesRepository,ApplicationModelRepository applicationModelRepository,
                                    ModelMapper mapper, ObjectMapper objectMapper, CoreValidations validations) {
        this.appCodesRepository = appCodesRepository;
        this.applicationModelRepository = applicationModelRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.validations = validations;
    }



    public ApplicationModuleResponse createAppModule(ApplicationModuleDto request) {
//        validations.validateBank(request);

        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        ApplicationModule applicationModule = mapper.map(request,ApplicationModule.class);

        AppCodes appCodeExist = appCodesRepository.findByAppCode(request.getAppCode());
        if(appCodeExist == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,"App code does not exist");
        }
        ApplicationModule appModuleExist = applicationModelRepository.findByAppCode(request.getAppCode());
        if(appModuleExist !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " App module already exist");
        }
        applicationModule.setCreatedBy(userCurrent.getId());
        applicationModule.setStatus("1");
        applicationModule = applicationModelRepository.save(applicationModule);
        log.debug("Create app module - {}"+ new Gson().toJson(applicationModule));
        return mapper.map(applicationModule, ApplicationModuleResponse.class);
    }



    public ApplicationModuleResponse updateAppModule(ApplicationModuleDto request) {
//        validations.validateBank(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        ApplicationModule appModuleExist = applicationModelRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested  id does not exist!"));
        mapper.map(request, appModuleExist);
        appModuleExist.setUpdatedBy(userCurrent.getId());
        appModuleExist.setUpdatedDate(LocalDateTime.now());
        applicationModelRepository.save(appModuleExist);
        log.debug("App module record updated - {}"+ new Gson().toJson(appModuleExist));
        return mapper.map(appModuleExist, ApplicationModuleResponse.class);
    }


    public ApplicationModuleResponse findAppModule(Long id){
        ApplicationModule applicationModule = applicationModelRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested id does not exist!"));
        return mapper.map(applicationModule,ApplicationModuleResponse.class);
    }


    public void enableDisable (EnableDisEnableDto request){
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        ApplicationModule applicationModule = applicationModelRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested module Id does not exist!"));
        applicationModule.setStatus(request.getStatus());
        applicationModule.setUpdatedBy(userCurrent.getId());
        applicationModelRepository.save(applicationModule);

    }



    public Page<ApplicationModule> findAll(String appCode, String name,String status, PageRequest pageRequest ){
        Page<ApplicationModule> applicationModules = applicationModelRepository.findAppModules(appCode,name,status,pageRequest);
        if(applicationModules == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return applicationModules;
    }


    public List<ApplicationModule> getAll(String appCode, String name,String status){
        List<ApplicationModule> applicationModules = applicationModelRepository.findAppModulesList(appCode,name,status);
        return applicationModules;

    }
}
