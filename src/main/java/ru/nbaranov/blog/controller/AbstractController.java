package ru.nbaranov.blog.controller;

import javassist.NotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import ru.nbaranov.blog.exception.EntityAlreadyExists;

import java.util.HashMap;
import java.util.Map;

public class AbstractController {
    @GetMapping("/ping")
    public Long ping() {
        return System.currentTimeMillis();
    }
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        return errors;
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundExceptions(
            NotFoundException ex) {
        return "";
    }

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Map<String, String> conflict(DataIntegrityViolationException e) {
        Map<String, String> errors = new HashMap<>();
        if (e instanceof EntityAlreadyExists) {
            errors.put(((EntityAlreadyExists) e).getField(), e.getMessage());
        }
        return errors;
    }
}
