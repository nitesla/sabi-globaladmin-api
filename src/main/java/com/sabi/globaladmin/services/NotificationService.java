package com.sabi.globaladmin.services;



import com.sabi.globaladmin.apihelper.API;
import com.sabi.globaladmin.notification.requestdto.NotificationRequestDto;
import com.sabi.globaladmin.notification.requestdto.RecipientRequest;
import com.sabi.globaladmin.notification.requestdto.SmsRequest;
import com.sabi.globaladmin.notification.responsedto.NotificationResponseDto;
import com.sabi.globaladmin.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@SuppressWarnings("ALL")
@Slf4j
@Service
public class NotificationService {


    @Value("${space.sms.url}")
    private String smsNotification;

    @Value("${space.notification.url}")
    private String multipleNotification;

    @Value("${authKey.notification}")
    private String authKey;

    @Value("${phoneNo.notification}")
    private String phoneNo;

    @Value("${notification.unique.id}")
    private String uniqueId;


    @Autowired
    private API api;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final ModelMapper mapper;

    public NotificationService( ModelMapper mapper) {
        this.mapper = mapper;
    }






    public void emailNotificationRequest (NotificationRequestDto notificationRequestDto){


        Map<String,String> map = new HashMap();
        map.put("auth-key", authKey);
        map.put("fingerprint", uniqueId);

        notificationRequestDto.setEmail(true);
        notificationRequestDto.setInApp(true);
        notificationRequestDto.setMessage(notificationRequestDto.getMessage());
        notificationRequestDto.getRecipient().forEach(p -> {
            RecipientRequest tran = RecipientRequest.builder()
                    .email(p.getEmail())
                    .build();
            p.setPhoneNo(phoneNo);
        });
        notificationRequestDto.setSms(false);
        notificationRequestDto.setTitle(Constants.NOTIFICATION);
        NotificationResponseDto response = api.post(multipleNotification, notificationRequestDto, NotificationResponseDto.class, map);


    }



    public void smsNotificationRequest (SmsRequest smsRequest){

        Map<String,String> map = new HashMap();
        map.put("fingerprint", uniqueId);

         api.post(smsNotification, smsRequest, String.class, map);


    }

}
