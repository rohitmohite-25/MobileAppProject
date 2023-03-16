package com.app.MobileAppProject.golbal;


import com.app.MobileAppProject.model.Product;

import java.util.ArrayList;
import java.util.List;

public class GlobalData {
    public static List<Product>cart;
    static {
        cart=new ArrayList<>();
    }
}

