package com.fiveeaglesti.clinicbackend.common;

import org.mockito.Mock;
import org.mockito.stubbing.OngoingStubbing;
import org.springframework.http.HttpStatus;

import java.util.Properties;

import static java.text.MessageFormat.format;
import static org.mockito.Mockito.when;

public abstract class AbstractTestHelper {

    protected static final String ERRO_CONSULTA_VAZIA = "Não há registros cadastrados.";
    protected static final String ERRO_OBJETO_NAO_ENCONTRADO = "Não foi possível encontrar {0}.";
    protected static final String ERRO_CAMPO_OBRIGATORIO = "{0} é um campo obrigatório.";
    protected static final String ACAO_SUCESSO = "Ação realizada com sucesso.";
    protected static final String SUCESSO_CRIACAO_OBJETO = "{0} foi criado com sucesso.";
    protected static final String SUCESSO_ALTERACAO_OBJETO = "{0} foi alterado com sucesso.";
    protected static final String SUCESSO_EXCLUSAO_OBJETO = "{0} foi excluído com sucesso.";
    protected static final String ERRO_PARAMETRO_NAO_ENCONTRADO = "O parâmetro {0} não foi encontrado.";
    protected static final Integer HTTP_BAD_REQUEST_VALUE = HttpStatus.BAD_REQUEST.value();
    protected static final Integer HTTP_CREATED_VALUE = HttpStatus.CREATED.value();
    protected static final Integer HTTP_OK_VALUE = HttpStatus.OK.value();

    @Mock
    protected Properties messages;

    public OngoingStubbing<String> emptyResponseMessage() {
        return when(messages.getProperty("erro.consulta.vazia"))
                .thenReturn(ERRO_CONSULTA_VAZIA);
    }

    public OngoingStubbing<String> buildSuccessResponse() {
        return when(messages.getProperty("acao.sucesso"))
                .thenReturn(ACAO_SUCESSO);
    }

    public OngoingStubbing<String> buildUpdateSuccessResponse(String entity) {
        return when(messages.getProperty("sucesso.alteracao.objeto"))
                .thenReturn(formatMessage(SUCESSO_ALTERACAO_OBJETO, entity));
    }

    public OngoingStubbing<String> buildCreateSuccessResponse(String entity) {
        return when(messages.getProperty("sucesso.criacao.objeto"))
                .thenReturn(formatMessage(SUCESSO_CRIACAO_OBJETO, entity));
    }

    public OngoingStubbing<String> buildDeleteSuccessResponse(String entity) {
        return when(messages.getProperty("sucesso.exclusao.objeto"))
                .thenReturn(formatMessage(SUCESSO_EXCLUSAO_OBJETO, entity));
    }

    public OngoingStubbing<String> objectNotFoundMessage(String entity) {
        return when(messages.getProperty("erro.objeto.nao.encontrado"))
                .thenReturn(formatMessage(ERRO_OBJETO_NAO_ENCONTRADO, entity));
    }

    public OngoingStubbing<String> requiredFieldMessage(String parameter) {
        return when(messages.getProperty("erro.campo.obrigatorio"))
                .thenReturn(formatMessage(ERRO_CAMPO_OBRIGATORIO, parameter));
    }

    public OngoingStubbing<String> buildRequestWithoutParameterResponse(String parameter) {
        return when(messages.getProperty("erro.parametro.nao.encontrado"))
                .thenReturn(formatMessage(ERRO_PARAMETRO_NAO_ENCONTRADO, parameter));
    }

    public String formatMessage(String message, Object... parameters) {
        return format(message, parameters);
    }
}
