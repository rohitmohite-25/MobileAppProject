package com.app.MobileAppProject.controller;

import com.app.MobileAppProject.golbal.GlobalData;
import com.app.MobileAppProject.invoice.QRCode;
import com.app.MobileAppProject.invoice.custData;
import com.app.MobileAppProject.model.Category;
import com.app.MobileAppProject.model.Customer;
import com.app.MobileAppProject.model.Product;
import com.app.MobileAppProject.service.CustomerService;
import com.app.MobileAppProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.io.IOException;

@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @Autowired
    CustomerService customerService;
    @GetMapping("/addToCart/{id}")
    public  String addToCart(@PathVariable int id)
    {
        GlobalData.cart.add(productService.getProductById(id).get());
        QRCode qrCode=new QRCode();
        qrCode.generateQrCode(productService.getProductById(id).get().getPrize());
        return "redirect:/shop";
    }
    @GetMapping("/cart")
    public  String cartGet(Model model)
    {
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrize).sum());
        model.addAttribute("cart",GlobalData.cart);
        return "cart";
    }
    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index)
    {
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }
    @GetMapping("/checkout")
    public  String checkout(Model model,Customer customer)
    {
        model.addAttribute("total",GlobalData.cart.stream().mapToDouble(Product::getPrize).sum());
        System.out.println(customer.toString());

        System.out.println(customer.getFirstName());
        System.out.println(customer.getLastName());
        System.out.println(customer.getAddressLine1());
        System.out.println(customer.getAddressLine2());
        System.out.println(customer.getPostCode());
        System.out.println(customer.getCity());
        System.out.println(customer.getPhoneNumber());
        System.out.println(customer.getEmailAddress());
        System.out.println(customer.getAdditionalInformation());
        model.addAttribute("firstName",customer.getFirstName());
        model.addAttribute("lastName",customer.getLastName());
        return "checkout";
    }
    @GetMapping("/payNow")
    public  String payment()
    {
        return "payment";
    }
    @GetMapping("/genrateInvoice")
    public  String invoice() throws IOException {
//        custData cd = new custData();
//        cd.invoice();
//        cd.getData();
//        cd.checkingData();
        return "invoiceEnd";
    }
    @GetMapping("/cash")
    public  String cod()
    {
        return "redirect:";
    }

    @GetMapping("/QR-CODE")
    public  String QRCode()
    {
        return "QRCode";
    }

    @GetMapping("/cardsuccess")
    public  String cardsuccess()
    {
        return "redirect:";
    }

    @PostMapping("/customeruser")
    public String customeruser(@ModelAttribute("Customer") Customer customer) {

        System.out.println(customer);

        customerService.customerUser(customer);

        return "successregistration";

    }

}
