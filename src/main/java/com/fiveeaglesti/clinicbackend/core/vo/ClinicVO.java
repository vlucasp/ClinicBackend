package com.fiveeaglesti.clinicbackend.core.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Builder @AllArgsConstructor
public class ClinicVO {

    private Long id;

    private String name;
}
