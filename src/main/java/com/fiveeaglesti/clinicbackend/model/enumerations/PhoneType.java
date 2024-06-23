package com.fiveeaglesti.clinicbackend.model.enumerations;

import lombok.Getter;
import lombok.Setter;

public enum PhoneType {

    CELULAR("Celular"), FIXO("Fixo");

    private PhoneType(String description) {
        this.description = description;
    }

    @Getter @Setter
    private String description;
}
