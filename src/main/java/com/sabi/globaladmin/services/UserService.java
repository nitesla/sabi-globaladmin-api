package com.sabi.globaladmin.services;


import com.google.gson.Gson;
import com.sabi.globaladmin.dto.requestdto.*;
import com.sabi.globaladmin.dto.responsedto.ActivateUserResponse;
import com.sabi.globaladmin.dto.responsedto.UserActivationResponse;
import com.sabi.globaladmin.dto.responsedto.UserResponse;
import com.sabi.globaladmin.exceptions.BadRequestException;
import com.sabi.globaladmin.exceptions.ConflictException;
import com.sabi.globaladmin.exceptions.NotFoundException;
import com.sabi.globaladmin.model.PreviousPasswords;
import com.sabi.globaladmin.model.Role;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.model.UserRole;
import com.sabi.globaladmin.notification.requestdto.NotificationRequestDto;
import com.sabi.globaladmin.notification.requestdto.RecipientRequest;
import com.sabi.globaladmin.notification.requestdto.SmsRequest;
import com.sabi.globaladmin.notification.requestdto.WhatsAppRequest;
import com.sabi.globaladmin.repository.PreviousPasswordRepository;
import com.sabi.globaladmin.repository.RoleRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    @Autowired
    private RoleRepository roleRepository;
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
        user.setStatus("0");
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

        String msg = "Hello " + " " + user.getFirstName() + " " + user.getLastName() + "<br/>"
                + "Activation OTP :" + " "+ user.getResetToken() + "<br/>"
                + " Kindly click the link below to complete your registration " + "<br/>"
                + "<a href=\"" + request.getActivationUrl() +  "\">Activate your account</a>";

        NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
        User emailRecipient = userRepository.getOne(user.getId());
        notificationRequestDto.setMessage(msg);
        List<RecipientRequest> recipient = new ArrayList<>();
        recipient.add(RecipientRequest.builder()
                .email(emailRecipient.getEmail())
                .build());
        notificationRequestDto.setRecipient(recipient);
        notificationService.emailNotificationRequest(notificationRequestDto);


        SmsRequest smsRequest = SmsRequest.builder()
                .message(msg)
                .phoneNumber(emailRecipient.getPhone())
                .build();
        notificationService.smsNotificationRequest(smsRequest);


        WhatsAppRequest whatsAppRequest = WhatsAppRequest.builder()
                .message(msg)
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




    public UserResponse updateUser(UpdateUserDto request,HttpServletRequest request1) {
        coreValidations.updateUser(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested user id does not exist!"));
        mapper.map(request, user);
        user.setUpdatedBy(userCurrent.getId());
        userRepository.save(user);
        log.debug("user record updated - {}"+ new Gson().toJson(user));


        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Update user by username:" + userCurrent.getUsername(),
                        AuditTrailFlag.UPDATE,
                        " Update user Request for:" + user.getId() + " "+ user.getUsername(),1, Utility.getClientIp(request1));
        return mapper.map(user, UserResponse.class);
    }



    public UserResponse findUser(Long id){
        User user  = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested user id does not exist!"));

        Role role = roleRepository.getOne(user.getRoleId());
        UserResponse userResponse = UserResponse.builder()
                .createdBy(user.getCreatedBy())
                .createdDate(user.getCreatedDate())
                .email(user.getEmail())
                .failedLoginDate(user.getFailedLoginDate())
                .firstName(user.getFirstName())
                .id(user.getId())
                .status(user.getStatus())
                .lastLogin(user.getLastLogin())
                .lastName(user.getLastName())
                .middleName(user.getMiddleName())
                .phone(user.getPhone())
                .updatedBy(user.getUpdatedBy())
                .updatedDate(user.getUpdatedDate())
                .roleId(user.getRoleId())
                .roleName(role.getName())
                .build();

        return userResponse;
    }


    public Page<User> findAll(String firstName, String lastName, String phone, String status, String email, PageRequest pageRequest ){
        Page<User> users = userRepository.findUsers(firstName,lastName,phone,status,email,pageRequest);
        if(users == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, " No record found !");
        }
        return users;

    }


    public void enableDisEnableUser (EnableDisEnableDto request, HttpServletRequest request1){
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        User user  = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested user id does not exist!"));
        user.setStatus(request.getStatus());
        user.setUpdatedBy(userCurrent.getId());

        auditTrailService
                .logEvent(userCurrent.getUsername(),
                        "Disable/Enable user by :" + userCurrent.getUsername() ,
                        AuditTrailFlag.UPDATE,
                        " Disable/Enable user Request for:" +  user.getId()
                                + " " +  user.getUsername(),1, Utility.getClientIp(request1));
        userRepository.save(user);

    }



    public void changeUserPassword(ChangePasswordDto request) {
        coreValidations.changePassword(request);
        User userCurrent = TokenService.getCurrentUserFromSecurityContext();
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested user id does not exist!"));
        mapper.map(request, user);
        if(getPrevPasswords(user.getId(),request.getPassword())){
            throw new ConflictException(CustomResponseCode.CONFLICT_EXCEPTION, " Password already used");
        }
        if (!getPrevPasswords(user.getId(), request.getPreviousPassword())) {
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, "Invalid previous password");
        }
        String password = request.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setStatus(CustomResponseCode.ACTIVE_USER);
        user.setLockedDate(null);
        user.setUpdatedBy(userCurrent.getId());
        user = userRepository.save(user);

        PreviousPasswords previousPasswords = PreviousPasswords.builder()
                .userId(user.getId())
                .password(user.getPassword())
                .createdDate(LocalDateTime.now())
                .build();
        previousPasswordRepository.save(previousPasswords);

    }


    /** <summary>
     * Previous password
     * </summary>
     * <remarks>this method is responsible for fetching the last 4 passwords</remarks>
     */

    public Boolean getPrevPasswords(Long userId,String password){
        List<PreviousPasswords> prev = previousPasswordRepository.previousPasswords(userId);
        for (PreviousPasswords pass : prev
                ) {
            if (passwordEncoder.matches(password, pass.getPassword())) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }



    public void unlockAccounts (UnlockAccountRequestDto request) {
        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested user id does not exist!"));
        mapper.map(request, user);
        user.setLockedDate(null);
        user.setLoginAttempts(0);
        userRepository.save(user);

    }




    public void lockLogin(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested user id does not exist!"));
        user.setLockedDate(new Date());
        userRepository.save(user);
    }




    public  void forgetPassword (ForgetPasswordDto request) {

        if(request.getEmail() != null) {

            User user = userRepository.findByEmail(request.getEmail());
            if (user == null) {
                throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, "Invalid email");
            }
            if (user.getStatus() == CustomResponseCode.DEACTIVE_USER) {
                throw new BadRequestException(CustomResponseCode.FAILED, "User account has been disabled");
            }
            user.setResetToken(Utility.registrationCode("HHmmss"));
            user.setResetTokenExpirationDate(Utility.tokenExpiration());
            userRepository.save(user);

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

        }else if(request.getPhone()!= null) {

            User userPhone = userRepository.findByPhone(request.getPhone());
            if (userPhone == null) {
                throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, "Invalid phone number");
            }
            if (userPhone.getStatus() == CustomResponseCode.DEACTIVE_USER) {
                throw new BadRequestException(CustomResponseCode.FAILED, "User account has been disabled");
            }
            userPhone.setResetToken(Utility.registrationCode("HHmmss"));
            userPhone.setResetTokenExpirationDate(Utility.tokenExpiration());
            userRepository.save(userPhone);


            NotificationRequestDto notificationRequestDto = new NotificationRequestDto();
            User emailRecipient = userRepository.getOne(userPhone.getId());
            notificationRequestDto.setMessage("Activation Otp " + " " + userPhone.getResetToken());
            List<RecipientRequest> recipient = new ArrayList<>();
            recipient.add(RecipientRequest.builder()
                    .email(emailRecipient.getEmail())
                    .build());
            notificationRequestDto.setRecipient(recipient);
            notificationService.emailNotificationRequest(notificationRequestDto);

            SmsRequest smsRequest = SmsRequest.builder()
                    .message("Activation Otp " + " " + userPhone.getResetToken())
                    .phoneNumber(emailRecipient.getPhone())
                    .build();
            notificationService.smsNotificationRequest(smsRequest);

            WhatsAppRequest whatsAppRequest = WhatsAppRequest.builder()
                    .message("Activation Otp " + " " + userPhone.getResetToken())
                    .phoneNumber(emailRecipient.getPhone())
                    .build();
            whatsAppService.whatsAppNotification(whatsAppRequest);
        }

    }



    public ActivateUserResponse activateUser (ActivateUserAccountDto request) {
        User user = userRepository.findByResetToken(request.getResetToken());
        if(user == null){
            throw new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION, "Invalid OTP supplied");
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Calendar calobj = Calendar.getInstance();
        String currentDate = df.format(calobj.getTime());
        String regDate = user.getResetTokenExpirationDate();
        String result = String.valueOf(currentDate.compareTo(regDate));
        if(!result.startsWith("-")){
            throw new BadRequestException(CustomResponseCode.BAD_REQUEST, " OTP invalid/expired");
        }

        request.setUpdatedBy(0l);
        request.setStatus(CustomResponseCode.ACTIVE_USER);
        request.setPasswordChangedOn(LocalDateTime.now());
        userOTPValidation(user,request);

        ActivateUserResponse response = ActivateUserResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .build();

        return response;

    }



    public User userOTPValidation(User user, ActivateUserAccountDto activateUserAccountDto) {
        user.setUpdatedBy(activateUserAccountDto.getUpdatedBy());
        user.setStatus(activateUserAccountDto.getStatus());
        user.setPasswordChangedOn(activateUserAccountDto.getPasswordChangedOn());
        return userRepository.saveAndFlush(user);
    }



    public User loginUser(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername());
        if (null == user) {
            return null;
        } else {

            if (passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
                user.setLoginStatus(true);
            } else {
                user.setLoginStatus(false);
            }
            return user;
        }

    }



    public void updateFailedLogin(Long id){
        User userExist = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " user id does not exist!"));
        if(userExist != null){
            userExist.setFailedLoginDate(LocalDateTime.now());

            int count = increment(userExist.getLoginAttempts());
            userExist.setLoginAttempts(count);
            userRepository.save(userExist);

        }
    }




    public static int increment(int number){
        String string_num = Integer.toString(number);

        int len = string_num.length();
        String add = "";

        for (int i = 0; i < len; i++) {
            add = add.concat("1");
        }
        int str_num = Integer.parseInt(add);

        System.out.println(number + str_num);

        return number + str_num;
    }




    public void updateLogin(Long id){
        User userExist = userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        " user id does not exist!"));
        if(userExist != null){
            userExist.setLockedDate(null);
            userExist.setLoginAttempts(0);
            userExist.setLastLogin(LocalDateTime.now());
            userRepository.save(userExist);

        }
    }






    public UserActivationResponse userPasswordActivation(PasswordActivationRequest request) {

        User user = userRepository.findById(request.getId())
                .orElseThrow(() -> new NotFoundException(CustomResponseCode.NOT_FOUND_EXCEPTION,
                        "Requested user id does not exist!"));
        mapper.map(request, user);

        String password = request.getPassword();
        user.setPassword(passwordEncoder.encode(password));
        user.setPasswordChangedOn(LocalDateTime.now());
        user = userRepository.save(user);

        PreviousPasswords previousPasswords = PreviousPasswords.builder()
                .userId(user.getId())
                .password(user.getPassword())
                .createdDate(LocalDateTime.now())
                .build();
        previousPasswordRepository.save(previousPasswords);


        UserActivationResponse response = UserActivationResponse.builder()
                .userId(user.getId())
                .phone(user.getPhone())
                .email(user.getEmail())
                .username(user.getUsername())
                .build();

        return response;
    }


    public long getSessionExpiry() {
        //TODO Token expiry in seconds: 900 = 15mins
        return tokenTimeToLeave / 60;
    }

}
