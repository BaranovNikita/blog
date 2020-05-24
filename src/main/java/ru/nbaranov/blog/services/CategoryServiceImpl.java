package ru.nbaranov.blog.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import ru.nbaranov.blog.entity.Category;
import ru.nbaranov.blog.exception.EntityAlreadyExists;
import ru.nbaranov.blog.reportistory.CategoryRepository;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    @Autowired
    CategoryRepository repository;

    @Override
    public List<Category> getAll() {
        return repository.findAll();
    }

    @Override
    public Category createCategory(Category category) throws EntityAlreadyExists {
        try {
            return repository.save(category);
        } catch (DataIntegrityViolationException e) {
            throw new EntityAlreadyExists("title", "Category with current title already exists");
        }
    }

    @Override
    public Category getCategory(Long id) {
        return repository.findById(id).orElse(null);
    }
}
