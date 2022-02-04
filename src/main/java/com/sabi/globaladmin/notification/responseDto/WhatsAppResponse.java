package com.sabi.globaladmin.notification.responseDto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class WhatsAppResponse {

    private String message;
    private String code;
}
