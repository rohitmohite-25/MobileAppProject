package com.app.MobileAppProject.reposistory;

import com.app.MobileAppProject.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerReposistory extends JpaRepository<Customer,Integer> {
}
