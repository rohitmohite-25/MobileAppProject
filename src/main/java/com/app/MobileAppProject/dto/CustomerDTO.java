package com.app.MobileAppProject.dto;

import com.app.MobileAppProject.model.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
public class CustomerDTO {
    private int id;
    private String firstname;
    private  String lastname;
    private  String A1;
    private  String A2;
    private int Product_Id;
    private int pincode;
    private  String city;
    private  String phno;
    private  String email;
}
