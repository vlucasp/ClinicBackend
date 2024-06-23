package com.fiveeaglesti.clinicbackend.client.common;

import com.fiveeaglesti.clinicbackend.client.dto.ResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Properties;

import static java.text.MessageFormat.format;

@Component
public class AbstractController {
    private static final String SUCESSO_CRIACAO_OBJETO = "sucesso.criacao.objeto";
    private static final String SUCESSO_ALTERACAO_OBJETO = "sucesso.alteracao.objeto";
    private static final String SUCESSO_EXCLUSAO_OBJETO = "sucesso.exclusao.objeto";
    private static final String ACAO_SUCESSO = "acao.sucesso";
    private static final String ERRO_INESPERADO = "erro.erro.inesperado";
    private static final String ERRO_PARAMETRO_NAO_ENCONTRADO = "erro.parametro.nao.encontrado";
    private static final String ERRO_CAMPO_OBRIGATORIO = "erro.campo.obrigatorio";

    @Autowired
    @Qualifier("messages")
    private Properties messages;

    public ResponseEntity<Object> buildBadRequestResponse(Exception e) {
        return ResponseEntity.badRequest()
                .body(
                        ResponseDTO.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(e.getMessage())
                                .build()
                );
    }

    public ResponseEntity<Object> buildRequestWithoutParameterResponse(String parameter) {
        return ResponseEntity.badRequest()
                .body(
                        ResponseDTO.builder()
                                .status(HttpStatus.BAD_REQUEST.value())
                                .message(format(messages.getProperty(ERRO_PARAMETRO_NAO_ENCONTRADO), parameter))
                                .build()
                );
    }

    public ResponseEntity<Object> buildInternalServerErrorResponse(Exception e) {
        return ResponseEntity.badRequest()
                .body(
                        ResponseDTO.builder()
                                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                                .message(format(messages.getProperty(ERRO_INESPERADO), e.getMessage()))
                                .build()
                );
    }

    public ResponseEntity<Object> buildCreateSuccessResponse(Object object, String entity) {
        return ResponseEntity.ok()
                .body(
                        ResponseDTO.builder()
                                .status(HttpStatus.CREATED.value())
                                .message(format(messages.getProperty(SUCESSO_CRIACAO_OBJETO), entity))
                                .body(object)
                                .build()
                );
    }

    public ResponseEntity<Object> buildUpdateSuccessResponse(Object object, String entity) {
        return ResponseEntity.ok()
                .body(
                        ResponseDTO.builder()
                                .status(HttpStatus.OK.value())
                                .message(format(messages.getProperty(SUCESSO_ALTERACAO_OBJETO), entity))
                                .body(object)
                                .build()
                );
    }

    public ResponseEntity<Object> buildDeleteSuccessResponse(String entity) {
        return ResponseEntity.ok()
                .body(
                        ResponseDTO.builder()
                                .status(HttpStatus.OK.value())
                                .message(format(messages.getProperty(SUCESSO_EXCLUSAO_OBJETO), entity))
                                .build()
                );
    }

    public ResponseEntity<Object> buildSuccessResponse(Object object) {
        return ResponseEntity.ok()
                .body(
                        ResponseDTO.builder()
                                .status(HttpStatus.OK.value())
                                .message(messages.getProperty(ACAO_SUCESSO))
                                .body(object)
                                .build()
                );
    }

    public String buildRequiredFieldMessage(String field) {
        return format(messages.getProperty(ERRO_CAMPO_OBRIGATORIO), field);
    }
}
