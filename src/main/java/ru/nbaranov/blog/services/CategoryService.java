package ru.nbaranov.blog.services;

import ru.nbaranov.blog.entity.Category;
import ru.nbaranov.blog.exception.EntityAlreadyExists;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category createCategory(Category category) throws EntityAlreadyExists;
    Category getCategory(Long id);
}
