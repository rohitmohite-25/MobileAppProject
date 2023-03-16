package com.app.MobileAppProject.controller;


import com.app.MobileAppProject.golbal.GlobalData;
import com.app.MobileAppProject.model.Role;
import com.app.MobileAppProject.model.User;
import com.app.MobileAppProject.reposistory.RoleReposistory;
import com.app.MobileAppProject.reposistory.UserReposistory;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.List;

@Controller
public class LoginConroller {
  // @Autowired
//  private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    UserReposistory userReposistory;
    @Autowired
    RoleReposistory roleReposistory;
    @GetMapping("/login")
    private  String login()
    {
        GlobalData.cart.clear();
        return"login";
    }
    @GetMapping("/register")
    private  String registerGet()
    {
        return "register";
    }
    @PostMapping("/register")
    public  String registerPost(@ModelAttribute("user") User user, HttpServletRequest request)throws ServletException
    {
//        String password=user.getPassword();
//        user.setPassword(bcry);
        List<Role> roles=new ArrayList<>();
        roles.add(roleReposistory.findById(2).get());
        user.setRoles(roles);
        request.login(user.getEmail(),user.getPassword());

        return "redirect:/";
    }

}
