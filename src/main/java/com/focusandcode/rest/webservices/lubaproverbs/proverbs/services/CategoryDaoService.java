package com.focusandcode.rest.webservices.lubaproverbs.proverbs.services;

import com.focusandcode.rest.webservices.lubaproverbs.proverbs.domain.Category;
import com.focusandcode.rest.webservices.lubaproverbs.proverbs.exceptions.CategoryNotFoundException;
import com.focusandcode.rest.webservices.lubaproverbs.proverbs.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryDaoService {
    private CategoryRepository categoryRepository;

    @Autowired
    private CategoryDaoService(CategoryRepository categoryRepository) {
        Assert.notNull(categoryRepository, "categoryRepository cannot be null");
        this.categoryRepository = categoryRepository;
    }

    public Category save(Category category) {
        return this.categoryRepository.save(category);
    }

    public List<Category> findAll() {
        return this.categoryRepository.findAll();
    }

    public Category findOne(String id) {
        Optional<Category> byId = this.categoryRepository.findById(id);
        if (byId.isPresent()) {
            return byId.get();
        }
        throw new CategoryNotFoundException("No category exist for id-" + id );
    }

    public Category delete(String id) {
        Category category = findOne(id);
        this.categoryRepository.delete(category);
        return category;
    }
}
