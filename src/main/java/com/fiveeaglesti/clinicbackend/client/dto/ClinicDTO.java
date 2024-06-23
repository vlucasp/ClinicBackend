package com.fiveeaglesti.clinicbackend.client.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor
@Builder @AllArgsConstructor
public class ClinicDTO {

    private Long id;

    private String name;
}
