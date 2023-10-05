package com.minecrafteiros.EnchantUtils.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/")
@Controller
public class homeController {
    @GetMapping
    public String loadForm() {
        return "/home";
    }
}
