package com.jf.datadict.controller;

import com.jf.datadict.model.JSONResult;
import com.jf.datadict.service.CustomService;
import com.jf.datadict.service.DetailService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@RestController
public class DataBaseController {

    @Resource
    private DetailService dataBaseService;

    @Resource
    private CustomService customService;

    @ResponseBody
    @GetMapping("/queryAllDataBase")
    public JSONResult queryAllDataBase() {
        return dataBaseService.queryAllDataBase();
    }

    @ResponseBody
    @GetMapping("/queryAllDBOfCustom")
    public JSONResult queryAllDBOfCustom(HttpSession session) {
        return customService.queryAllDBOfCustom(session);
    }

}
