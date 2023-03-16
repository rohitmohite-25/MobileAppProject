package com.app.MobileAppProject.dto;

import lombok.Data;

@Data
public class ProductDTO {
    private long id;
    private String name;
    private int CategoryId;
    private double Price;
    private String os;
    private  String camera;
    private  String processor;
    private  String storage;
    private  String ram;
    private String description;
    private String imageName;

}
