package com.uniovi.notaneitor.controllers;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
@Controller
public class HomeController {

    //Asi, en el UML base de la pagina, lleva al INDEX
    @RequestMapping("/")
    public String index() {
        return "index";
    }
}