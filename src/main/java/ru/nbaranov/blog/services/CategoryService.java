package ru.nbaranov.blog.services;

import ru.nbaranov.blog.entity.Category;
import ru.nbaranov.blog.exception.CategoryAlreadyExists;

import java.util.List;

public interface CategoryService {
    List<Category> getAll();
    Category createCategory(Category category) throws CategoryAlreadyExists;
}
