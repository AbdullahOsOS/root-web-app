package com.root.meter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ChartController {
    @GetMapping("/me/chart")
    public String getChart(){
        return "chart";
    }
}
