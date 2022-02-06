package com.sabi.globaladmin.notification.responsedto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@SuppressWarnings("ALL")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class NotificationResponseData {


    private String description;
    private String body;
    private String title;
    private String summary;

}
