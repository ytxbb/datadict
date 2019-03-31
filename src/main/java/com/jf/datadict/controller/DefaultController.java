package com.jf.datadict.controller;

import com.jf.datadict.constants.ReturnCode;
import com.jf.datadict.entity.User;
import com.jf.datadict.model.JSONResult;
import com.jf.datadict.service.DataBaseService;
import com.jf.datadict.service.DataStatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DefaultController {

    @Resource
    private DataBaseService dataBaseService;

    @Resource
    private DataStatisticsService dataStatisticsService;

    @GetMapping("/")
    public String index(Model model){
        JSONResult res = dataStatisticsService.queryDataStatistics();
        if (res.getStatus().equals(ReturnCode.Code_200)) {
            model.addAttribute("dataStatistics", res.getData());
        }
        return "index";
    }

    @ResponseBody
    @GetMapping("/queryAllDataBase")
    public JSONResult queryAllDataBase() {
        return dataBaseService.queryAllDataBase();
    }

    @ResponseBody
    @GetMapping("/queryDataStatistics")
    public JSONResult queryDataStatistics() {
        return dataStatisticsService.queryDataStatistics();
    }
}
