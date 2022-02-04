package com.sabi.globaladmin.services;


import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.UserDto;
import com.sabi.globaladmin.dto.responsedto.UserResponse;
import com.sabi.globaladmin.exceptions.ConflictException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.PreviousPasswords;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.model.UserRole;
import com.sabi.globaladmin.notification.requestDto.NotificationRequestDto;
import com.sabi.globaladmin.notification.requestDto.RecipientRequest;
import com.sabi.globaladmin.notification.requestDto.SmsRequest;
import com.sabi.globaladmin.notification.requestDto.WhatsAppRequest;
import com.sabi.globaladmin.repository.PreviousPasswordRepository;
import com.sabi.globaladmin.repository.UserRepository;
import com.sabi.globaladmin.repository.UserRoleRepository;
import com.sabi.globaladmin.utils.AuditTrailFlag;
import com.sabi.globaladmin.utils.Constants;
import com.sabi.globaladmin.utils.CustomResponseCode;
import com.sabi.globaladmin.utils.Utility;
import com.sabi.globaladmin.validations.CoreValidations;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@SuppressWarnings("ALL")
@Service
public class UserService {

    @Value("${token.time.to.leave}")
    long tokenTimeToLeave;


    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private PreviousPasswordRepository previousPasswordRepository;
    @Autowired
    private UserRoleRepository userRoleRepository;
    private UserRepository userRepository;
    private NotificationService notificationService;
    private final ModelMapper mapper;
    private final CoreValidations coreValidations;
    private final AuditTrailService auditTrailService;
    private final WhatsAppService whatsAppService;




    public UserService(UserRepository userRepository,NotificationService notificationService,
                       ModelMapper mapper,CoreValidations coreValidations,AuditTrailService auditTrailService,
                       WhatsAppService whatsAppService) {
        this.userRepository = userRepository;
        this.notificationService = notificationService;
        this.mapper = mapper;
        this.coreValidations = coreValidations;
        this.auditTrailService = auditTrailService;
        this.whatsAppService = whatsAppService;


    }





    public UserResponse createUser(UserDto request, HttpServletRequest request1) {
        coreValidations.validateUser(request);
        User userExist = userRepository.findByEmailOrPhone(request.getEmail(),request.getPhone());
        if(userExist !=null){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " User already exist");
        }
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        User user = mapper.map(request,User.class);
        String password = Utility.getSaltString();
        user.setPassword(passwordEncoder.encode(password));
        user.setUsername(request.getEmail());
        user.setCreatedBy(userCurrent.getId());
        user.setUserCategory(Constants.ADMIN_USER);
        user.setStatus(0);
        user.setLoginAttempts(0);
        user.setResetToken(Utility.registrationCode("HHmmss"));
        user.setResetTokenExpirationDate(Utility.tokenExpiration());
        user = userRepository.save(user);
        log.debug("Create new user - {}"+ new Gson().toJson(user));

        UserRole userRole = UserRole.builder()
                .userId(user.getId())
                .roleId(user.getRoleId())
                .createdDate(LocalDateTime.now())
                .build();
        userRoleRepository.save(userRole);

        PreviousPasswords previousPasswords = PreviousPasswords.builder()
                .userId(user.getId())
                .password(user.getPassword())
                .createdDate(LocalDateTime.now())
                .build();
        previousPasswordRepository.save(previousPasswords);

        // --------  sending token  -----------


        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        User emailRecipient = userRepository.getOne(user.getId());
        notificationRequestDto.setMessage("Activation Otp " + " " + user.getResetToken());
        List<RecipientRequest> recipient = new ArrayList<>();
        recipient.add(RecipientRequest.builder()
                .email(emailRecipient.getEmail())
                .build());
        notificationRequestDto.setRecipient(recipient);
        notificationService.emailNotificationRequest(notificationRequestDto);

        SmsRequest smsRequest = SmsRequest.builder()
                .message("Activation Otp " + " " + user.getResetToken())
                .phoneNumber(emailRecipient.getPhone())
                .build();
        notificationService.smsNotificationRequest(smsRequest);


        WhatsAppRequest whatsAppRequest = WhatsAppRequest.builder()
                .message("Activation Otp " + " " + user.getResetToken())
                .phoneNumber(emailRecipient.getPhone())
                .build();
        whatsAppService.whatsAppNotification(whatsAppRequest);

        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Create new user by :" + userCurrent.getUsername(),
                        AuditTrailFlag.CREATE,
                        " Create new user for:" + user.getFirstName() + " " + user.getUsername(),1, Utility.getClientIp(request1));
        return mapper.map(user, UserResponse.class);
    }




    public UserResponse updateUser(UserDto request,HttpServletRequest request1) {
        coreValidations.updateUser(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested user id does not exist!"));
        mapper.map(request, user);
        user.setUpdatedBy(userCurrent.getId());
        userRepository.save(user);
        log.debug("user record updated - {}"+ new Gson().toJson(user));

        UserRole userRole = userRoleRepository.findByUserId(user.getId());
        UserRole roleUser = userRoleRepository.getOne(userRole.getId());
        roleUser.setRoleId(user.getRoleId());
        userRoleRepository.save(roleUser);

        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Update user by username:" + userCurrent.getUsername(),
                        AuditTrailFlag.UPDATE,
                        " Update user Request for:" + user.getId() + " "+ user.getUsername(),1, Utility.getClientIp(request1));
        return mapper.map(user, UserResponse.class);
    }





}
