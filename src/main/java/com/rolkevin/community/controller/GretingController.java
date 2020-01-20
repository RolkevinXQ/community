package com.rolkevin.community.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/basic")
public class GretingController {
    @GetMapping("/")
    public String getGreting(@RequestParam(name = "name") String name, Model model) {
        model.addAttribute("name", name);
        return "index";
    }
}
