package com.fiveeaglesti.clinicbackend.core.service;

import com.fiveeaglesti.clinicbackend.core.helper.NegocioExceptionHelper;
import com.fiveeaglesti.clinicbackend.core.vo.ClinicVO;

import java.util.List;

public interface ClinicService {
    List<ClinicVO> findAllClinics() throws NegocioExceptionHelper;

    ClinicVO findClinicById(Long id) throws NegocioExceptionHelper;

    ClinicVO createClinic(ClinicVO vo) throws NegocioExceptionHelper;

    ClinicVO updateClinic(ClinicVO vo) throws NegocioExceptionHelper;

    void deleteClinicById(Long id) throws NegocioExceptionHelper;
}
