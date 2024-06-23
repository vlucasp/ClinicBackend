package com.fiveeaglesti.clinicbackend.model.mapper;

import com.fiveeaglesti.clinicbackend.client.dto.ClinicDTO;
import com.fiveeaglesti.clinicbackend.core.vo.ClinicVO;
import com.fiveeaglesti.clinicbackend.model.entity.Clinic;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ClinicMapper {

    ClinicMapper INSTANCE = Mappers.getMapper(ClinicMapper.class);

    ClinicVO clinicToVO(Clinic clinic);

    Clinic voToClinic(ClinicVO vo);

    ClinicVO dtoToVO(ClinicDTO dto);

    ClinicDTO voToDTO(ClinicVO vo);
}
