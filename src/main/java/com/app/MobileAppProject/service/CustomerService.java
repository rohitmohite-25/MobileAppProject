package com.app.MobileAppProject.service;

import com.app.MobileAppProject.model.Category;
import com.app.MobileAppProject.model.Customer;
import com.app.MobileAppProject.reposistory.CustomerReposistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    @Autowired
    private CustomerReposistory customerReposistory;

    public List<Customer> getAllCustomers() {
        return customerReposistory.findAll();
    }

    public void addCstomer(Customer customer) {
        customerReposistory.save(customer);
    }

    public void removeCategoryById(int id) {
        customerReposistory.deleteById(id);
    }
    public void customerUser(Customer customer) {

        customerReposistory.save(customer);
    }
}
