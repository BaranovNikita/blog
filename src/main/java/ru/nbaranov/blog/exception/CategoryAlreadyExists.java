package ru.nbaranov.blog.exception;

import org.springframework.dao.DataIntegrityViolationException;

public class CategoryAlreadyExists extends DataIntegrityViolationException {
    public static String ERROR_MSG = "Category with current name already exists";
    public CategoryAlreadyExists() {
        super(ERROR_MSG);
    }
}
