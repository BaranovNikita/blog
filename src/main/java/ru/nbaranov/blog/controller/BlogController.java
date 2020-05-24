package ru.nbaranov.blog.controller;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import ru.nbaranov.blog.entity.Category;
import ru.nbaranov.blog.exception.CategoryAlreadyExists;
import ru.nbaranov.blog.services.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/", produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = { MediaType.APPLICATION_JSON_VALUE })
public class BlogController {
    @Autowired
    CategoryService categoryService;

    @GetMapping("/ping")
    public Long ping() {
        return System.currentTimeMillis();
    }

    @GetMapping("/category")
    @ResponseBody
    public List<Category> getCategoryList() {
        return categoryService.getAll();
    }

    @PostMapping(value = "/category", produces = { MediaType.TEXT_PLAIN_VALUE })
    @ResponseBody
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok("OK");
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

    @ResponseStatus(value = HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public Map<String, String> conflict(DataIntegrityViolationException e) {
        Map<String, String> errors = new HashMap<>();
        if (e instanceof CategoryAlreadyExists) {
            errors.put("title", e.getMessage());
        }

        return errors;
    }
}
