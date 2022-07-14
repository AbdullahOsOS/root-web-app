package com.root.meter.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
    @GetMapping("/")
    public String get(){
        return "index";
    }

    @GetMapping("/index")
    public String get2(){
        return "index";
    }
}
