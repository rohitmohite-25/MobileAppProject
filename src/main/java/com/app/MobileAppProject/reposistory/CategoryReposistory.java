package com.app.MobileAppProject.reposistory;


import com.app.MobileAppProject.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryReposistory extends JpaRepository<Category, Integer> {
}
