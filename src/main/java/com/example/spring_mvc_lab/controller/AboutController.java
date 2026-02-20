package com.example.spring_mvc_lab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AboutController {
    @GetMapping("/about")
    public String about(Model model){

        model.addAttribute("textHello", "Hello Rifan Radedo from about page");
        model.addAttribute("tempatTanggalLahir", "Medan, 27 Juni 2007");
        model.addAttribute("alamat", "Ramunia Indonesia");
        model.addAttribute("email", "2481043@unai.edu");
        model.addAttribute("noTelepon", "083851525240");
        model.addAttribute("pendidikan", "Kuliah");
        model.addAttribute("hobi", "Bermain Bola");

        return "about";
    }
}