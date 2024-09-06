package com.roberto.fucturacontato.exceptions;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationError  extends StandardError {

    private List<FieldMessage> erros = new ArrayList<>();

    public ValidationError() {}

    public ValidationError(LocalDateTime timestamp, Integer status, String message, String path) {
        super(timestamp, status, message, path);
    }

    public List<FieldMessage> getErrors() {
        return erros;
    }

    public void addError(String fieldName, String message ) {
        this.erros.add(new FieldMessage(fieldName, message));
    }
}
