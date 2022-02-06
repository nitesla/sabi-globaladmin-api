package com.sabi.globaladmin.notification.requestdto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WhatsAppRequest {

    private String message;
    private String phoneNumber;
}
