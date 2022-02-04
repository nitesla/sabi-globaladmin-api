package com.sabi.globaladmin.dto.requestdto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EnableDisEnableDto {

     private Long id;
     private int status;
}
