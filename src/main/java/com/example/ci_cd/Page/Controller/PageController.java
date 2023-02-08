package com.example.ci_cd.Page.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    @GetMapping
    public String indexController(){
        return "index";
    }
}
