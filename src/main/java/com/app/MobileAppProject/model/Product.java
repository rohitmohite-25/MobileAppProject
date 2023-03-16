package com.app.MobileAppProject.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "product_Id")
    private long id;
    @Column(name="product_name")
    private String name;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Category_id", referencedColumnName = "Category_id")
    private Category category;
    private double Prize;
    private String os;
    private  String camera;
    private  String processor;
    private  String storage;
    private  String ram;
    private String description;
    private String imageName;

}
