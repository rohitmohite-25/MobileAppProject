package com.app.MobileAppProject.service;


import com.app.MobileAppProject.model.Category;
import com.app.MobileAppProject.reposistory.CategoryReposistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryReposistory categoryReposistory;

    public List<Category> getAllCategory() {
        return categoryReposistory.findAll();
    }

    public void addCategory(Category category) {
        categoryReposistory.save(category);
    }

    public void removeCategoryById(int id) {
        categoryReposistory.deleteById(id);
    }

    public Optional<Category> getCategoryById(int id) {
        return categoryReposistory.findById(id);
    }
}
