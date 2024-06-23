package com.fiveeaglesti.clinicbackend.core.service.impl.common;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Properties;

import static java.text.MessageFormat.format;

@Service
public abstract class AbstractService {
    private static final String ERRO_OBJETO_NAO_ENCONTRADO = "erro.objeto.nao.encontrado";
    private static final String ERRO_CONSULTA_VAZIA = "erro.consulta.vazia";
    private static final String ERRO_CAMPO_OBRIGATORIO = "erro.campo.obrigatorio";

    @Autowired
    @Qualifier("messages")
    private Properties messages;

    public String objectNotFoundMessage(String objeto) {
        return format(messages.getProperty(ERRO_OBJETO_NAO_ENCONTRADO), objeto);
    }

    public String requiredFieldMessage(String parameter) {
        return format(messages.getProperty(ERRO_CAMPO_OBRIGATORIO), parameter);
    }

    public String emptyResponseMessage() {
        return messages.getProperty(ERRO_CONSULTA_VAZIA);
    }

    public static void copyProperties(Object source, Object dest, String... propertyNames) {
        if (source == null) {
            throw new IllegalArgumentException("\"source\": null");
        } else if (dest == null) {
            throw new IllegalArgumentException("\"dest\": null");
        } else if (propertyNames != null) {
            try {
                for (String propertyName : propertyNames) {
                    Object sourcePropertyValue = PropertyUtils.getProperty(source, propertyName);
                    PropertyUtils.setProperty(dest, propertyName, sourcePropertyValue);
                }
            } catch (Exception e) {
                throw new IllegalStateException("Erro ao mapear propriedades do objeto.", e);
            }
        }
    }
}
