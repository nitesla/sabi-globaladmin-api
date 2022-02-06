package com.sabi.globaladmin.notification.requestdto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@SuppressWarnings("ALL")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Notify {
    private String email;
    private String phoneNo;
}
