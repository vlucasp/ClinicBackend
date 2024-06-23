package com.fiveeaglesti.clinicbackend.client;

import com.fiveeaglesti.clinicbackend.client.controller.ClinicController;
import com.fiveeaglesti.clinicbackend.client.dto.ClinicDTO;
import com.fiveeaglesti.clinicbackend.client.dto.ResponseDTO;
import com.fiveeaglesti.clinicbackend.common.AbstractTestHelper;
import com.fiveeaglesti.clinicbackend.core.helper.NegocioExceptionHelper;
import com.fiveeaglesti.clinicbackend.core.service.ClinicService;
import com.fiveeaglesti.clinicbackend.core.vo.ClinicVO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ClinicControllerTest extends AbstractTestHelper {

    private static final Long CLINIC_ID = 1L;
    private static final String CLINIC_NAME = "Clinic Test";
    private static final String CLINICA = "Clínica";
    private static final String NOME = "Nome";
    private static final String ID = "Id";

    @InjectMocks
    ClinicController clinicController;

    @Mock
    ClinicService clinicService;

    ClinicVO clinicVO;

    ClinicDTO clinicDTO;

    @BeforeEach
    public void setUp() {
        clinicVO = ClinicVO.builder()
                .id(CLINIC_ID)
                .name(CLINIC_NAME)
                .build();
        clinicDTO = ClinicDTO.builder()
                .id(CLINIC_ID)
                .name(CLINIC_NAME)
                .build();
    }

    @Test
    void testSuccessFindAllClinics() throws NegocioExceptionHelper {
        buildSuccessResponse();

        when(clinicService.findAllClinics())
                .thenReturn(Collections.singletonList(clinicVO));

        ResponseEntity<Object> result = clinicController.findAllClinics();

        assertNotNull(result);
        assertNotNull(result.getBody());

        ResponseDTO response = (ResponseDTO) result.getBody();
        assertEquals(HTTP_OK_VALUE, response.getStatus());
        assertEquals(ACAO_SUCESSO, response.getMessage());
        assertTrue(response.getBody() instanceof List<?>);

        verify(clinicService).findAllClinics();
        verifyNoMoreInteractions(clinicService);
    }

    @Test
    void testFailedFindAllClinicsEmptyList() throws NegocioExceptionHelper {
        when(clinicService.findAllClinics())
                .thenThrow(new NegocioExceptionHelper(ERRO_CONSULTA_VAZIA));

        ResponseEntity<Object> result = clinicController.findAllClinics();

        assertNotNull(result);
        assertNotNull(result.getBody());

        ResponseDTO response = (ResponseDTO) result.getBody();
        assertEquals(HTTP_BAD_REQUEST_VALUE, response.getStatus());
        assertEquals(ERRO_CONSULTA_VAZIA, response.getMessage());
        assertNull(response.getBody());

        verify(clinicService).findAllClinics();
        verifyNoMoreInteractions(clinicService);
    }

    @Test
    void testSuccessFindClinicById() throws NegocioExceptionHelper{
        buildSuccessResponse();

        when(clinicService.findClinicById(CLINIC_ID))
                .thenReturn(clinicVO);

        ResponseEntity<Object> result = clinicController.findClinicById(CLINIC_ID);

        assertNotNull(result);
        assertNotNull(result.getBody());

        ResponseDTO response = (ResponseDTO) result.getBody();
        assertEquals(HTTP_OK_VALUE, response.getStatus());
        assertEquals(ACAO_SUCESSO, response.getMessage());
        assertTrue(response.getBody() instanceof ClinicDTO);

        verify(clinicService).findClinicById(CLINIC_ID);
        verifyNoMoreInteractions(clinicService);
    }

    @Test
    void testFailedFindClinicByIdNotFound() throws NegocioExceptionHelper {
        String formattedMessage = formatMessage(ERRO_OBJETO_NAO_ENCONTRADO, CLINICA);
        when(clinicService.findClinicById(CLINIC_ID))
                .thenThrow(new NegocioExceptionHelper(formattedMessage));

        ResponseEntity<Object> result = clinicController.findClinicById(CLINIC_ID);

        assertNotNull(result);
        assertNotNull(result.getBody());

        ResponseDTO response = (ResponseDTO) result.getBody();
        assertEquals(HTTP_BAD_REQUEST_VALUE, response.getStatus());
        assertEquals(formattedMessage, response.getMessage());
        assertNull(response.getBody());

        verify(clinicService).findClinicById(CLINIC_ID);
        verifyNoMoreInteractions(clinicService);
    }

    @Test
    void testSuccessCreateClinic() throws NegocioExceptionHelper {
        buildCreateSuccessResponse(CLINICA);

        when(clinicService.createClinic(ClinicVO.builder()
                .name(CLINIC_NAME)
                .build()))
                .thenReturn(clinicVO);

        ResponseEntity<Object> result = clinicController.createClinic(ClinicDTO.builder()
                .name(CLINIC_NAME)
                .build());

        assertNotNull(result);
        assertNotNull(result.getBody());

        ResponseDTO response = (ResponseDTO) result.getBody();
        assertEquals(HTTP_CREATED_VALUE, response.getStatus());
        assertEquals(formatMessage(SUCESSO_CRIACAO_OBJETO, CLINICA), response.getMessage());
        assertTrue(response.getBody() instanceof ClinicDTO);

        verifyNoMoreInteractions(clinicService);
    }

    @Test
    void testFailedCreateClinicRequiredField() throws NegocioExceptionHelper {
        String formattedMessage = formatMessage(ERRO_CAMPO_OBRIGATORIO, NOME);
        when(clinicService.createClinic(ClinicVO.builder().build()))
                .thenThrow(new NegocioExceptionHelper(formattedMessage));

        ResponseEntity<Object> result = clinicController.createClinic(ClinicDTO.builder().build());

        assertNotNull(result);
        assertNotNull(result.getBody());

        ResponseDTO response = (ResponseDTO) result.getBody();
        assertEquals(HTTP_BAD_REQUEST_VALUE, response.getStatus());
        assertEquals(formattedMessage, response.getMessage());
        assertNull(response.getBody());

        verifyNoMoreInteractions(clinicService);
    }

    @Test
    void testSuccessUpdateClinic() throws NegocioExceptionHelper {
        buildUpdateSuccessResponse(CLINICA);

        when(clinicService.updateClinic(clinicVO))
                .thenReturn(clinicVO);

        ResponseEntity<Object> result = clinicController.updateClinic(clinicDTO);

        assertNotNull(result);
        assertNotNull(result.getBody());

        ResponseDTO response = (ResponseDTO) result.getBody();
        assertEquals(HTTP_OK_VALUE, response.getStatus());
        assertEquals(formatMessage(SUCESSO_ALTERACAO_OBJETO, CLINICA), response.getMessage());
        assertTrue(response.getBody() instanceof ClinicDTO);

        verifyNoMoreInteractions(clinicService);
    }

    @Test
    void testFailedUpdateClinicRequiredField() {
        buildRequestWithoutParameterResponse(ID);

        ResponseEntity<Object> result = clinicController.updateClinic(ClinicDTO.builder().build());

        assertNotNull(result);
        assertNotNull(result.getBody());

        ResponseDTO response = (ResponseDTO) result.getBody();
        assertEquals(HTTP_BAD_REQUEST_VALUE, response.getStatus());
        assertEquals(formatMessage(ERRO_PARAMETRO_NAO_ENCONTRADO, ID), response.getMessage());
        assertNull(response.getBody());

        verifyNoInteractions(clinicService);
    }

    @Test
    void testSuccessDeleteClinicById() throws NegocioExceptionHelper {
        buildDeleteSuccessResponse(CLINICA);

        ResponseEntity<Object> result = clinicController.deleteClinic(CLINIC_ID);

        assertNotNull(result);
        assertNotNull(result.getBody());

        ResponseDTO response = (ResponseDTO) result.getBody();
        assertEquals(HTTP_OK_VALUE, response.getStatus());
        assertEquals(formatMessage(SUCESSO_EXCLUSAO_OBJETO, CLINICA), response.getMessage());
        assertNull(response.getBody());

        verify(clinicService).deleteClinicById(CLINIC_ID);
        verifyNoMoreInteractions(clinicService);
    }

    @Test
    void testFailedDeleteClinicNotFound() throws NegocioExceptionHelper {
        String formattedMessage = formatMessage(ERRO_OBJETO_NAO_ENCONTRADO, CLINICA);

        doThrow(new NegocioExceptionHelper(formattedMessage))
                .when(clinicService).deleteClinicById(CLINIC_ID);

        ResponseEntity<Object> result = clinicController.deleteClinic(CLINIC_ID);

        assertNotNull(result);
        assertNotNull(result.getBody());

        ResponseDTO response = (ResponseDTO) result.getBody();
        assertEquals(HTTP_BAD_REQUEST_VALUE, response.getStatus());
        assertEquals(formattedMessage, response.getMessage());
        assertNull(response.getBody());

        verifyNoMoreInteractions(clinicService);
    }
}
