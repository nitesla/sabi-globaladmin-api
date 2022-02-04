package com.sabi.globaladmin.services;


import com.sabi.globaladmin.apihelper.API;
import com.sabi.globaladmin.notification.requestDto.WhatsAppRequest;
import com.sabi.globaladmin.notification.responseDto.WhatsAppResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings("ALL")
@Slf4j
@Service
public class WhatsAppService {


    @Value("${authKey.whatsapp}")
    private String whatsAuthKey;

    @Value("${notification.unique.id}")
    private String uniqueId;

    @Value("${whatsapp.notification.url}")
    private String whatsAppNotification;


    @Autowired
    private API api;


    public WhatsAppResponse whatsAppNotification(WhatsAppRequest whatsAppRequest){

        Map<String,String> map = new HashMap();
        map.put("auth-key", whatsAuthKey.trim());
        map.put("fingerprint", uniqueId.trim());

        WhatsAppResponse response = api.post(whatsAppNotification.trim(), whatsAppRequest, WhatsAppResponse.class, map);
        return response;

    }
}
