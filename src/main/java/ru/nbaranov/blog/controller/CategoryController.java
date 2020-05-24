package ru.nbaranov.blog.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.nbaranov.blog.entity.Category;
import ru.nbaranov.blog.services.CategoryService;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/category")
public class CategoryController extends AbstractController {
    @Autowired
    CategoryService categoryService;

    @GetMapping
    public List<Category> getCategoryList() {
        return categoryService.getAll();
    }

    @PostMapping(produces = { MediaType.TEXT_PLAIN_VALUE })
    public ResponseEntity<String> createCategory(@Valid @RequestBody Category category) {
        categoryService.createCategory(category);
        return ResponseEntity.ok("OK");
    }
}
