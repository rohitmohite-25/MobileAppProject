package com.app.MobileAppProject.reposistory;

import com.app.MobileAppProject.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductReposistory extends JpaRepository<Product, Long> {
    List<Product> findAllByCategory_id(int id);
}
