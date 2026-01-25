package com.busreservation.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Home controller for the application.
 * Handles the landing page and general navigation.
 */
@Controller
public class HomeController {

    /**
     * Display home page.
     */
    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("pageTitle", "Bus Reservation System");
        return "index";
    }
}
