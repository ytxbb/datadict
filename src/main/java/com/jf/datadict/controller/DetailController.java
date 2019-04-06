package com.jf.datadict.controller;

import com.jf.datadict.model.JSONResult;
import com.jf.datadict.service.DetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class DetailController {

    @Resource
    private DetailService detailService;

    @ResponseBody
    @PostMapping("/queryMenuList")
    public JSONResult queryMenuList(String dbName){
        return detailService.queryMenuList(dbName);
    }
}
