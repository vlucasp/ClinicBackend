package com.fiveeaglesti.clinicbackend.core;

import com.fiveeaglesti.clinicbackend.common.AbstractTestHelper;
import com.fiveeaglesti.clinicbackend.core.helper.NegocioExceptionHelper;
import com.fiveeaglesti.clinicbackend.core.service.impl.ClinicServiceImpl;
import com.fiveeaglesti.clinicbackend.core.vo.ClinicVO;
import com.fiveeaglesti.clinicbackend.model.entity.Clinic;
import com.fiveeaglesti.clinicbackend.model.repository.ClinicRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClinicServiceTest extends AbstractTestHelper {

    private static final Long CLINIC_ID = 1L;
    private static final String CLINIC_NAME = "Clinic Test";
    private static final String CLINICA = "Clínica";
    private static final String NOME = "Nome";

    @InjectMocks
    ClinicServiceImpl clinicService;

    @Mock
    ClinicRepository clinicRepository;

    Clinic clinic;

    ClinicVO clinicVO;

    @BeforeEach
    public void setUp() {
        clinic = Clinic.builder()
                .id(CLINIC_ID)
                .name(CLINIC_NAME)
                .build();
        clinicVO = ClinicVO.builder()
                .id(CLINIC_ID)
                .name(CLINIC_NAME)
                .build();
    }

    @Test
    void testSuccessFindAllClinics() throws NegocioExceptionHelper {
        when(clinicRepository.findAll())
                .thenReturn(Collections.singletonList(clinic));

        List<ClinicVO> vos = clinicService.findAllClinics();

        assertFalse(vos.isEmpty());
        assertEquals(vos.size(), 1);
        assertEquals(vos.getFirst().getId(), clinic.getId());
        verify(clinicRepository).findAll();
        verifyNoMoreInteractions(clinicRepository);
    }

    @Test
    void testFailedFindAllClinicsEmptyList() {
        emptyResponseMessage();

        when(clinicRepository.findAll())
                .thenReturn(Collections.emptyList());

        final NegocioExceptionHelper e = assertThrows(NegocioExceptionHelper.class, () -> {
            clinicService.findAllClinics();
        });

        assertNotNull(e);
        assertEquals(ERRO_CONSULTA_VAZIA, e.getMessage());
        verify(clinicRepository).findAll();
        verifyNoMoreInteractions(clinicRepository);
    }

    @Test
    void testSuccessFindClinicById() throws NegocioExceptionHelper {
        when(clinicRepository.findById(CLINIC_ID))
                .thenReturn(Optional.of(clinic));

        ClinicVO vo = clinicService.findClinicById(CLINIC_ID);

        assertNotNull(vo);
        assertEquals(vo.getId(), CLINIC_ID);
        verifyNoMoreInteractions(clinicRepository);
    }

    @Test
    void testFailedFindClinicByIdNotFound() {
        objectNotFoundMessage(CLINICA);

        when(clinicRepository.findById(CLINIC_ID))
                .thenReturn(Optional.empty());

        final NegocioExceptionHelper e = assertThrows(NegocioExceptionHelper.class, () -> {
            clinicService.findClinicById(CLINIC_ID);
        });

        assertNotNull(e);
        assertEquals(formatMessage(ERRO_OBJETO_NAO_ENCONTRADO, CLINICA), e.getMessage());
        verifyNoMoreInteractions(clinicRepository);
    }

    @Test
    void testSuccessCreateClinic() throws NegocioExceptionHelper {
        when(clinicRepository.save(Clinic.builder()
                .name(CLINIC_NAME)
                .build()))
                .thenReturn(clinic);

        ClinicVO vo = clinicService.createClinic(ClinicVO.builder()
                .name(CLINIC_NAME)
                .build());

        assertNotNull(vo);
        assertEquals(CLINIC_ID, vo.getId());
        assertEquals(CLINIC_NAME, vo.getName());
        verifyNoMoreInteractions(clinicRepository);
    }

    @Test
    void testFailedCreateClinicRequiredField() {
        requiredFieldMessage(NOME);

        final NegocioExceptionHelper e = assertThrows(NegocioExceptionHelper.class, () -> {
            clinicService.createClinic(ClinicVO.builder().build());
        });

        assertNotNull(e);
        assertEquals(formatMessage(ERRO_CAMPO_OBRIGATORIO, NOME), e.getMessage());
        verifyNoMoreInteractions(clinicRepository);
    }

    @Test
    void testSuccessUpdateClinic() throws NegocioExceptionHelper {
        when(clinicRepository.save(clinic))
                .thenReturn(clinic);

        when(clinicRepository.findById(CLINIC_ID))
                .thenReturn(Optional.of(clinic));

        ClinicVO vo = clinicService.updateClinic(clinicVO);

        assertNotNull(vo);
        assertEquals(CLINIC_ID, vo.getId());
        assertEquals(CLINIC_NAME, vo.getName());
        verifyNoMoreInteractions(clinicRepository);
    }

    @Test
    void testFailedUpdateClinicNotFound() {
        objectNotFoundMessage(CLINICA);

        when(clinicRepository.findById(CLINIC_ID))
                .thenReturn(Optional.empty());

        final NegocioExceptionHelper e = assertThrows(NegocioExceptionHelper.class, () -> {
            clinicService.updateClinic(clinicVO);
        });

        assertNotNull(e);
        assertEquals(formatMessage(ERRO_OBJETO_NAO_ENCONTRADO, CLINICA), e.getMessage());
        verifyNoMoreInteractions(clinicRepository);
    }

    @Test
    void testSuccessDeleteClinicById() throws NegocioExceptionHelper {
        when(clinicRepository.findById(CLINIC_ID))
                .thenReturn(Optional.of(clinic));

        clinicService.deleteClinicById(CLINIC_ID);

        verify(clinicRepository).delete(clinic);
        verifyNoMoreInteractions(clinicRepository);
    }

    @Test
    void testFailedDeleteClinicByIdNotFound() {
        objectNotFoundMessage(CLINICA);

        when(clinicRepository.findById(CLINIC_ID))
                .thenReturn(Optional.empty());

        final NegocioExceptionHelper e = assertThrows(NegocioExceptionHelper.class, () -> {
            clinicService.deleteClinicById(CLINIC_ID);
        });

        assertNotNull(e);
        assertEquals(formatMessage(ERRO_OBJETO_NAO_ENCONTRADO, CLINICA), e.getMessage());
        verifyNoMoreInteractions(clinicRepository);
    }

}
