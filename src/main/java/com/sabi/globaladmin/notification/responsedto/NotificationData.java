package com.sabi.globaladmin.notification.responsedto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@SuppressWarnings("ALL")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationData {

    private String message;
    private NotificationResponseData data;

}
