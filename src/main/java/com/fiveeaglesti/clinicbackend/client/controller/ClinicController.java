package com.fiveeaglesti.clinicbackend.client.controller;

import com.fiveeaglesti.clinicbackend.client.common.AbstractController;
import com.fiveeaglesti.clinicbackend.client.dto.ClinicDTO;
import com.fiveeaglesti.clinicbackend.core.helper.NegocioExceptionHelper;
import com.fiveeaglesti.clinicbackend.core.service.ClinicService;
import com.fiveeaglesti.clinicbackend.core.vo.ClinicVO;
import com.fiveeaglesti.clinicbackend.model.mapper.ClinicMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RestController
@RequestMapping("/clinic")
public class ClinicController extends AbstractController {

    private static final String CLINICA = "Clínica";

    private static final ClinicMapper CLINIC_MAPPER = ClinicMapper.INSTANCE;

    @Autowired
    private ClinicService clinicService;

    @GetMapping
    public ResponseEntity<Object> findAllClinics() {
        try {
            List<ClinicVO> clinics = clinicService.findAllClinics();

            return buildSuccessResponse(clinics.stream()
                    .map(CLINIC_MAPPER::voToDTO)
                    .collect(Collectors.toList()));
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> findClinicById(@PathVariable Long id) {
        try {
            ClinicVO vo = clinicService.findClinicById(id);
            return buildSuccessResponse(CLINIC_MAPPER.voToDTO(vo));
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }

    @PostMapping
    public ResponseEntity<Object> createClinic(@RequestBody ClinicDTO dto) {
        try {
            ClinicVO vo = clinicService.createClinic(CLINIC_MAPPER.dtoToVO(dto));
            return buildCreateSuccessResponse(CLINIC_MAPPER.voToDTO(vo), CLINICA);
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }

    @PutMapping
    public ResponseEntity<Object> updateClinic(@RequestBody ClinicDTO dto) {
        try {
            String requiredField = validateRequiredFields(dto);
            if (requiredField != null) {
                return buildRequestWithoutParameterResponse(requiredField);
            }
            ClinicVO vo = clinicService.updateClinic(CLINIC_MAPPER.dtoToVO(dto));
            return buildUpdateSuccessResponse(CLINIC_MAPPER.voToDTO(vo), CLINICA);
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteClinic(@PathVariable Long id) {
        try {
            clinicService.deleteClinicById(id);
            return buildDeleteSuccessResponse(CLINICA);
        } catch (NegocioExceptionHelper n) {
            return buildBadRequestResponse(n);
        } catch (Exception e) {
            return buildInternalServerErrorResponse(e);
        }
    }

    private String validateRequiredFields(ClinicDTO dto) {
        if (dto.getId() == null) {
            return "Id";
        }
        return null;
    }
}
