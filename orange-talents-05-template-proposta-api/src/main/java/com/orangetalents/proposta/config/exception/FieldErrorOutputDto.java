package com.orangetalents.proposta.config.exception;

public class FieldErrorOutputDto {
    private String field;
    private String message;

    public FieldErrorOutputDto() {
    }

    public FieldErrorOutputDto(String field, String message) {
        this.field = limpaField(field);
        this.message = message;
    }

    private String limpaField(String field) {
        return field.replaceAll("\\[.*?\\]", "")
                .replaceAll("\\<.*?\\>", "")
                .replaceAll("\\.$", "")
                .replaceAll("\\.", "_");
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}
