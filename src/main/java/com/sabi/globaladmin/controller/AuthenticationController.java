package com.sabi.globaladmin.controller;


import com.sabi.globaladmin.dto.requestdto.LoginRequest;
import com.sabi.globaladmin.dto.responsedto.AccessTokenWithUserDetails;
import com.sabi.globaladmin.exceptions.LockedException;
import com.sabi.globaladmin.exceptions.UnauthorizedException;
import com.sabi.globaladmin.model.User;
import com.sabi.globaladmin.security.AuthenticationWithToken;
import com.sabi.globaladmin.services.AuditTrailService;
import com.sabi.globaladmin.services.TokenService;
import com.sabi.globaladmin.services.UserService;
import com.sabi.globaladmin.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@Slf4j
@SuppressWarnings("All")
@RestController
@RequestMapping(Constants.APP_CONTENT + "global/authenticate")
public class AuthenticationController {

    private  static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Value("${login.attempts}")
    private int loginAttempts;


    @Autowired
    private TokenService tokenService;


    private final UserService userService;
    private final AuditTrailService auditTrailService;

    public AuthenticationController(UserService userService,AuditTrailService auditTrailService) {
        this.userService = userService;
        this.auditTrailService=auditTrailService;
    }



    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody @Valid LoginRequest loginRequest, HttpServletRequest request)  {

        log.info(":::::::::: login Request %s:::::::::::::" + loginRequest.getUsername());
        String loginStatus;
        String ipAddress = Utility.getClientIp(request);
        User user = userService.loginUser(loginRequest);
        if (user != null) {
            if (user.isLoginStatus()) {
                //FIRST TIME LOGIN
                if (user.getPasswordChangedOn() == null || user.getStatus()==CustomResponseCode.DEACTIVE_USER) {
                    Response resp = new Response();
                    resp.setCode(CustomResponseCode.CHANGE_P_REQUIRED);
                    resp.setDescription("Change password Required, account has not been activated");
                    return new ResponseEntity<>(resp, HttpStatus.ACCEPTED);//202
                }
                if (user.getStatus()==CustomResponseCode.DEACTIVE_USER) {
                    Response resp = new Response();
                    resp.setCode(CustomResponseCode.FAILED);
                    resp.setDescription("User Account Deactivated, please contact Administrator");
                    return new ResponseEntity<>(resp, HttpStatus.INTERNAL_SERVER_ERROR);
                }
                if (user.getLoginAttempts() >= loginAttempts || user.getLockedDate() != null) {
                    // lock account after x failed attempts or locked date is not null
                    userService.lockLogin(user.getId());
                    throw new LockedException(CustomResponseCode.LOCKED_EXCEPTION, "Your account has been locked, kindly contact System Administrator");
                }

//                userService.validateGeneratedPassword(user.getId());
            } else {
                //update login failed count and failed login date
                loginStatus = "failed";
                auditTrailService
                        .logEvent(loginRequest.getUsername(), "Login by username :" + loginRequest.getUsername()
                                        + " " + loginStatus,
                                AuditTrailFlag.LOGIN, "Failed Login Request by :" + loginRequest.getUsername(),1, ipAddress);
                userService.updateFailedLogin(user.getId());
                throw new UnauthorizedException(CustomResponseCode.UNAUTHORIZED, "Invalid Login details.");
            }
        } else {
            //NO NEED TO update login failed count and failed login date SINCE IT DOES NOT EXIST
            throw new UnauthorizedException(CustomResponseCode.UNAUTHORIZED, "Login details does not exist");
        }
//        String accessList = permissionService.getPermissionsByUserId(user.getId());
        String accessList="";
        AuthenticationWithToken authWithToken = new AuthenticationWithToken(user, null,
                AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_USER,"+accessList));
        String newToken = "Bearer" +" "+this.tokenService.generateNewToken();
        authWithToken.setToken(newToken);
        tokenService.store(newToken, authWithToken);
        SecurityContextHolder.getContext().setAuthentication(authWithToken);
        userService.updateLogin(user.getId());

        String clientId= "";
        String referralCode="";
        String isEmailVerified="";


        AccessTokenWithUserDetails details = new AccessTokenWithUserDetails(newToken, user,
                accessList,userService.getSessionExpiry());

        auditTrailService
                .logEvent(loginRequest.getUsername(), "Login by username : " + loginRequest.getUsername(),
                        AuditTrailFlag.LOGIN, "Successful Login Request by : " + loginRequest.getUsername() , 1, ipAddress);

        return new ResponseEntity<>(details, HttpStatus.OK);
    }



    @PostMapping("/logout")
    @ResponseStatus(value = HttpStatus.OK)
    public boolean logout() {
        try {
            AuthenticationWithToken auth = (AuthenticationWithToken) SecurityContextHolder.getContext().getAuthentication();
            return tokenService.remove(auth.getToken());
        } catch (Exception ex) {
            logger.error(ex.getMessage());
            LoggerUtil.logError(logger, ex);
        }
        return false;
    }

}
