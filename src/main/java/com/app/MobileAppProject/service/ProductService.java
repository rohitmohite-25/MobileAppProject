package com.app.MobileAppProject.service;



import com.app.MobileAppProject.model.Product;
import com.app.MobileAppProject.reposistory.ProductReposistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    ProductReposistory productReposistory;

    public List<Product> getAllProduct() {
        return productReposistory.findAll();
    }

    public void addProduct(Product product) {
        productReposistory.save(product);
    }

    public void removeProductById(long id) {
        productReposistory.deleteById(id);
    }

    public Optional<Product> getProductById(long id) {
        return productReposistory.findById(id);
    }

    public List<Product> getAllProductByCategoryId(int id) {
        return productReposistory.findAllByCategory_id(id);
    }
}
