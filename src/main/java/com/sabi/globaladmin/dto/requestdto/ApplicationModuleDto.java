package com.sabi.globaladmin.dto.requestdto;


import lombok.Data;

@Data
public class ApplicationModuleDto {

    private Long id;
    private String appCode;
    private String name;
    private String description;
    private String url;
    private String icons;
}
