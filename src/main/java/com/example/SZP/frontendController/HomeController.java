package com.example.SZP.frontendController;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/api/v1")
    public String home() {
        return "index"; // Zwraca nazwę szablonu Thymeleaf (bez rozszerzenia .html)
    }
}