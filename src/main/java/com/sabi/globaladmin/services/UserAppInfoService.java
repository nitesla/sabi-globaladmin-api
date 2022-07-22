package com.sabi.globaladmin.services;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.AuthKeyRequest;
import com.sabi.globaladmin.dto.requestdto.UserAppInfoDto;
import com.sabi.globaladmin.dto.responsedto.UserAppAccessList;
import com.sabi.globaladmin.dto.responsedto.UserAppInfoResponse;
import com.sabi.globaladmin.dto.responsedto.UserInforResponse;
import com.sabi.globaladmin.exceptions.BadRequestException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.model.UserAppInfo;
import com.sabi.globaladmin.repository.ApplicationModelRepository;
import com.sabi.globaladmin.repository.UserAppInfoRepository;
import com.sabi.globaladmin.repository.UserRepository;
import com.sabi.globaladmin.utils.AESEncryption;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.utils.Utility;
import com.sabi.globaladmin.validations.CoreValidations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
public class UserAppInfoService {

    private ApplicationModelRepository applicationModelRepository;
    private UserAppInfoRepository userAppInfoRepository;
    private final ModelMapper mapper;
    private final ObjectMapper objectMapper;
    private final CoreValidations validations;
    private final UserRepository userRepository;
    private final PermissionService permissionService;

    public UserAppInfoService(ApplicationModelRepository applicationModelRepository,UserAppInfoRepository userAppInfoRepository,
                              ModelMapper mapper, ObjectMapper objectMapper,
                              CoreValidations validations,UserRepository userRepository,PermissionService permissionService) {
        this.applicationModelRepository = applicationModelRepository;
        this.userAppInfoRepository = userAppInfoRepository;
        this.mapper = mapper;
        this.objectMapper = objectMapper;
        this.validations = validations;
        this.userRepository = userRepository;
        this.permissionService = permissionService;
    }


    public UserAppInfoResponse createAppInfo(UserAppInfoDto request) {
        validations.validateUserAppInfo(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        UserAppInfo appinfo = mapper.map(request,UserAppInfo.class);
        String  key = UUID.randomUUID().toString().replace("-", "").substring(16);
        UserAppInfo exist = userAppInfoRepository.findByUserIdAndApplicationCode(userCurrent.getId(),request.getApplicationCode());
        if(exist == null){
        appinfo.setUserId(userCurrent.getId());
        appinfo.setUsername(userCurrent.getUsername());
        appinfo.setAuthKey(Utility.generateAuthKey());
        appinfo.setAuthKeyExpirationDate(Utility.expiredTime());
        appinfo.setActionDate(LocalDateTime.now());
        appinfo.setSecreteKey(key);
        appinfo.setIvKey(Utility.doRandomPassword(16));
        log.debug("Create app info - {}"+ new Gson().toJson(appinfo));
        appinfo = userAppInfoRepository.save(appinfo);

            List<UserAppAccessList> permissions=null;

            permissions = permissionService.getUserAppPermissionByUserId(appinfo.getUserId());
            UserAppInfoResponse response = UserAppInfoResponse.builder()
                    .actionDate(appinfo.getActionDate())
                    .applicationCode(appinfo.getApplicationCode())
                    .userId(appinfo.getUserId())
                    .username(appinfo.getUsername())
                    .token(appinfo.getToken())
                    .authKey(AESEncryption.encryptAES(appinfo.getAuthKey(),appinfo.getSecreteKey(),appinfo.getIvKey()))
                    .authKeyExpirationDate(appinfo.getAuthKeyExpirationDate())
                    .permissions(permissions)
                    .build();
            return response;

        }else {
            UserAppInfo updateInfo = userAppInfoRepository.getOne(exist.getId());
            updateInfo.setAuthKey(Utility.generateAuthKey());
            updateInfo.setAuthKeyExpirationDate(Utility.expiredTime());
            updateInfo.setActionDate(LocalDateTime.now());
            updateInfo.setToken(request.getToken());
            updateInfo.setSecreteKey(key);
            updateInfo.setIvKey(Utility.doRandomPassword(16));
            log.debug("Update app info - {}"+ new Gson().toJson(updateInfo));
            updateInfo=userAppInfoRepository.save(updateInfo);

            List<UserAppAccessList> permissions=null;

            permissions = permissionService.getUserAppPermissionByUserId(updateInfo.getUserId());

            UserAppInfoResponse response = UserAppInfoResponse.builder()
                    .actionDate(updateInfo.getActionDate())
                    .applicationCode(updateInfo.getApplicationCode())
                    .userId(updateInfo.getUserId())
                    .username(updateInfo.getUsername())
                    .token(updateInfo.getToken())
                    .authKey(AESEncryption.encryptAES(updateInfo.getAuthKey(),updateInfo.getSecreteKey(),updateInfo.getIvKey()))
                    .authKeyExpirationDate(updateInfo.getAuthKeyExpirationDate())
                    .permissions(permissions)
                    .build();
            return response;
        }
//        UserAppInfo findSavedRecord = userAppInfoRepository.findByUserId(userCurrent.getId());


    }


//    public UserAppInfoResponse updateAppInfo(UserAppInfoDto request) {
//
//        UserAppInfo appInfo = userAppInfoRepository.findById(request.getId())
//                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
//                        "Requested  id does not exist!"));
//        mapper.map(request, appInfo);
//
//        userAppInfoRepository.save(appInfo);
//        log.debug("App info record updated - {}"+ new Gson().toJson(appInfo));
//        return mapper.map(appInfo, UserAppInfoResponse.class);
//    }



    public UserInforResponse findAppInfo(Long id){
        UserAppInfo appInfo = userAppInfoRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested id does not exist!"));
        return mapper.map(appInfo,UserInforResponse.class);
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


    public UserInforResponse findByUserIdAndInfo(Long userId,String applicationCode){
        UserAppInfo appInfo = userAppInfoRepository.findByUserIdAndApplicationCode(userId,applicationCode);
        if(appInfo == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return mapper.map(appInfo,UserInforResponse.class);
    }


    public UserInforResponse findByAuthKey(AuthKeyRequest authKey){
//        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        UserAppInfo findSavedRecord = userAppInfoRepository.findByUserIdAndApplicationCode(authKey.getUserId(), authKey.getApplicationCode());
        if(findSavedRecord == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }

        String decryptAuthKey = AESEncryption.decryptAES(authKey.getAuthKey(),findSavedRecord.getSecreteKey(),findSavedRecord.getIvKey());
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calobj = Calendar.getInstance();
        String currentDate = df.format(calobj.getTime());
        String regDate = findSavedRecord.getAuthKeyExpirationDate();
        String result = String.valueOf(currentDate.compareTo(regDate));
        if(result.equals("1")){
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, " Auth key expired");
        }
        UserAppInfo appInfo = userAppInfoRepository.findByAuthKey(decryptAuthKey);
        if(appInfo == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        User user = userRepository.getOne(appInfo.getUserId());

        List<UserAppAccessList> permissions=null;

        permissions = permissionService.getUserAppPermissionByUserId(appInfo.getUserId());
        UserInforResponse userInforResponse = UserInforResponse.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhone())
                .userId(appInfo.getUserId())
                .username(appInfo.getUsername())
                .actionDate(appInfo.getActionDate())
                .email(user.getEmail())
                .applicationCode(appInfo.getApplicationCode())
                .token(appInfo.getToken())
                .authKeyExpirationDate(appInfo.getAuthKeyExpirationDate())
                .permissions(permissions)
                .build();

        return userInforResponse;
    }

}
