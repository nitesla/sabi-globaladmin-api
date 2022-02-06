package com.sabi.globaladmin.dto.responsedto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class StateResponseDto {

    private Long id;

    private String name;

    private Long countryId;

    private String countryName;

    private LocalDateTime createdDate;

    private LocalDateTime updatedDate;

    private Long createdBy;

    private Long updatedBy;


}
