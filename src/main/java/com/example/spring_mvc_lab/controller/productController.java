package com.example.spring_mvc_lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class productController {

    @GetMapping("/product")
    public String product(Model model){

        model.addAttribute("product1", "Laptop");
        model.addAttribute("price1", "Rp 8.000.000");

        model.addAttribute("product2", "Smartphone");
        model.addAttribute("price2", "Rp 3.000.000");

        model.addAttribute("product3", "Headset Gaming");
        model.addAttribute("price3", "Rp 500.000");

        return "product";
    }
}