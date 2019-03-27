package com.jf.datadict.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/defined")
public class DefinedController {

    @GetMapping("/testGo")
    public String go(){
        return "Go !";
    }
}
