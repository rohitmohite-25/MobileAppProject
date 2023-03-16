package com.app.MobileAppProject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "customer_Id")
    private int id;

    private String firstName;
    private  String lastName;
    private  String addressLine1;
    private  String addressLine2;
    private int postCode;
    private  String city;
    private  String phoneNumber;
    private  String emailAddress;
    private  String additionalInformation;

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", postCode=" + postCode +
                ", city='" + city + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                ", emailAddress='" + emailAddress + '\'' +
                ", additionalInformation='" + additionalInformation + '\'' +
                '}';
    }

//      @ManyToMany(cascade = {CascadeType.ALL})
//    @JoinTable(name = "Customer_Product", joinColumns = {@JoinColumn(name = "customer_id")}, inverseJoinColumns = {@JoinColumn(name = "product_id")})
//    Set< Product > projects = new HashSet< Product >();
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "product_id", referencedColumnName = "product_id")
//    private Product product;

//    public Set<Product> getProjects() {
//        return projects;
//    }
//
//    public void setProjects(Set<Product> projects) {
//        this.projects = projects;
//    }
}
