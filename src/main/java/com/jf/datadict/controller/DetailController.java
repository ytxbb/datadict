package com.jf.datadict.controller;

import com.jf.datadict.model.JSONResult;
import com.jf.datadict.service.DetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class DetailController {

    @Resource
    private DetailService detailService;

    @GetMapping("/show")
    public String show(){
        return "show";
    }

    @GetMapping("/show2")
    public String show2(){
        return "show";
    }

    @ResponseBody
    @PostMapping("/queryMenuList")
    public JSONResult queryMenuList(String dbName){
        return detailService.queryMenuList(dbName);
    }
}
