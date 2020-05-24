package ru.nbaranov.blog.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class EntityAlreadyExists extends DataIntegrityViolationException {
    private String field;
    public EntityAlreadyExists(String field, String message) {
        super(message);
        this.field = field;
    }

    public String getField() {
        return field;
    }
}
