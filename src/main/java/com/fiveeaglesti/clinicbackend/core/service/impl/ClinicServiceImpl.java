package com.fiveeaglesti.clinicbackend.core.service.impl;

import com.fiveeaglesti.clinicbackend.core.helper.NegocioExceptionHelper;
import com.fiveeaglesti.clinicbackend.core.service.ClinicService;
import com.fiveeaglesti.clinicbackend.core.service.impl.common.AbstractService;
import com.fiveeaglesti.clinicbackend.core.vo.ClinicVO;
import com.fiveeaglesti.clinicbackend.model.entity.Clinic;
import com.fiveeaglesti.clinicbackend.model.mapper.ClinicMapper;
import com.fiveeaglesti.clinicbackend.model.repository.ClinicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Service
public class ClinicServiceImpl extends AbstractService implements ClinicService {

    private static final String CLINICA = "Clínica";
    private static final String[] CLINIC_PROPERTIES = { "id", "name" };
    private static final ClinicMapper CLINIC_MAPPER = ClinicMapper.INSTANCE;

    @Autowired
    private ClinicRepository clinicRepository;

    @Override
    public List<ClinicVO> findAllClinics() throws NegocioExceptionHelper {
        try {
            List<Clinic> clinics = clinicRepository.findAll();

            if (clinics.isEmpty()) {
                throw new NegocioExceptionHelper(emptyResponseMessage());
            }


            return clinics.stream()
                    .map(CLINIC_MAPPER::clinicToVO)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ClinicVO findClinicById(Long id) throws NegocioExceptionHelper {
        try {
            Clinic clinic = findById(id);
            return CLINIC_MAPPER.clinicToVO(clinic);
        } catch (NegocioExceptionHelper n) {
            throw n;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ClinicVO createClinic(ClinicVO vo) throws NegocioExceptionHelper {
        try {
            validateRequiredFields(vo);
            Clinic clinic = clinicRepository.save(CLINIC_MAPPER.voToClinic(vo));
            return CLINIC_MAPPER.clinicToVO(clinic);
        } catch (NegocioExceptionHelper n) {
            throw n;
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public ClinicVO updateClinic(ClinicVO vo) throws NegocioExceptionHelper {
        try {
            validateRequiredFields(vo);
            Clinic clinic = findById(vo.getId());
            copyProperties(vo, clinic, CLINIC_PROPERTIES);
            clinicRepository.save(clinic);
            return CLINIC_MAPPER.clinicToVO(clinic);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void deleteClinicById(Long id) throws NegocioExceptionHelper {
        try {
            Clinic clinic = findById(id);
            clinicRepository.delete(clinic);
        } catch (NegocioExceptionHelper n) {
            throw n;
        } catch (Exception e) {
            throw e;
        }
    }

    private Clinic findById(Long id) throws NegocioExceptionHelper {
        return clinicRepository.findById(id)
            .orElseThrow(
                    () -> new NegocioExceptionHelper(objectNotFoundMessage(CLINICA))
            );
    }

    private void validateRequiredFields(ClinicVO vo) throws NegocioExceptionHelper {
        if (isBlank(vo.getName())) {
            throw new NegocioExceptionHelper(requiredFieldMessage("Nome"));
        }
    }
}
