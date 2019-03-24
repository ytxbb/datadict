package com.jf.datadict.controller;

import com.jf.datadict.entity.DataBaseName;
import com.jf.datadict.service.DataBaseService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.annotation.Resource;
import java.util.List;

@Controller
public class DefaultController {

    @Resource
    private DataBaseService dataBaseService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @GetMapping("/queryAllDataBase")
    public String queryAllDataBase(Model model) {
        List<DataBaseName> dataBaseNames = dataBaseService.queryAllDataBase();
        model.addAttribute("dataBaseNames", dataBaseNames);
        return "index";
    }
}
