package com.app.MobileAppProject.controller;


import com.app.MobileAppProject.dto.ProductDTO;
import com.app.MobileAppProject.model.Category;
import com.app.MobileAppProject.model.Product;
import com.app.MobileAppProject.model.admin;
import com.app.MobileAppProject.reposistory.AdminReposistory;
import com.app.MobileAppProject.reposistory.UserReposistory;
import com.app.MobileAppProject.service.CategoryService;
import com.app.MobileAppProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminContoller {
    public String usename = "Capricon";
    //public String uplodDir = "C:\\Users\\"+usename+"\\IdeaProjects\\MobileAppProject\\src\\main\\resources\\static\\productImages";
    public String currentDir = System.getProperty("user.dir");
    public String uplodDir=(currentDir+"\\src\\main\\resources\\static\\productImages");
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;


    //login
    @Autowired(required = true)
    private AdminReposistory adminReposistory;

    @GetMapping("/admin")
    public String login(Model model) {
        admin admin = new admin();
        model.addAttribute("admin", admin);

        return "adminlogin";
    }

    @PostMapping("/admin")
    public String loginUser(@ModelAttribute("admin") admin admin) {
        String adminId = admin.getAdminId();

        Optional<admin> admindata =adminReposistory.findById(adminId);

        if(admin.getPassword().equals(admindata.get().getPassword())) {

            return "adminHome";
        }

        else {

            //return "errorpassword";
            return  "adminHome";

        }

    }
    //main
    @GetMapping("/error")
    public String error() {
        return "<h1>Some Error Occur Please Try Again....</h1>";
    }

    //Category Section
    @GetMapping("/admin/adminhome")
    public String adminHome() {
        return "adminHome";
    }

    @GetMapping("admin/adminhome/categories")
    public String category(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/admin/adminhome/categories/add")
    public String getcategoryAdd(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/adminhome/categories/add")
    public String postcategoryAdd(@ModelAttribute("category") Category category) {
        categoryService.addCategory(category);
        return "redirect:/admin/adminhome/categories";
    }

    @GetMapping("/admin/adminhome/categories/delete/{id}")
    public String deleteCategory(@PathVariable int id) {
        categoryService.removeCategoryById(id);
        return "redirect:/admin/adminhome/categories";
    }

    @GetMapping("/admin/adminhome/categories/update/{id}")
    public String updateCategory(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        } else
            return "404";
        //return "redirect:/admin/categories";
    }

    //product Section
    @GetMapping("/admin/adminhome/products")
    public String products(Model model) {
        model.addAttribute("products", productService.getAllProduct());
        return "products";
    }

    @GetMapping("/admin/adminhome/products/add")
    public String getProductAdd(Model model) {
        model.addAttribute("productDTO", new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/adminhome/products/add")
    public String productAddPost(@ModelAttribute("productDTO") ProductDTO productDTO,
                                 @RequestParam("productImage") MultipartFile file,
                                 @RequestParam("imgName") String imgName) throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrize(productDTO.getPrice());
        product.setOs(productDTO.getOs());
        product.setCamera(productDTO.getCamera());
        product.setProcessor(productDTO.getProcessor());
        product.setStorage(productDTO.getStorage());
        product.setRam(productDTO.getRam());
        product.setDescription(productDTO.getDescription());
        product.setImageName(productDTO.getImageName());
        String imageUUID = null;
        if (!file.isEmpty()) {
            imageUUID = file.getOriginalFilename();
            Path filenamepath = Paths.get(uplodDir, imageUUID);
            Files.write(filenamepath, file.getBytes());
        } else {
            imageUUID = imgName;
        }
        product.setImageName(imageUUID);
        productService.addProduct(product);

        return "redirect:/admin/adminhome/products";
    }

    @GetMapping("/admin/adminhome/product/delete/{id}")
    public String deleteProduct(@PathVariable int id) {
        productService.removeProductById(id);
        return "redirect:/admin/adminhome/products";
    }

    @GetMapping("/admin/adminhome/product/update/{id}")
    public String updateProduct(@PathVariable long id, Model model) {
        Product product = productService.getProductById(id).get();
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setPrice(product.getPrize());
        productDTO.setOs(product.getOs());
        productDTO.setCamera(product.getCamera());
        productDTO.setProcessor(product.getProcessor());
        productDTO.setStorage(product.getStorage());
        productDTO.setRam(product.getRam());
        productDTO.setDescription(product.getDescription());
        productDTO.setImageName(product.getImageName());
        model.addAttribute("categories", categoryService.getAllCategory());
        model.addAttribute("productDTO", productDTO);
        return "productsAdd";
    }


}
