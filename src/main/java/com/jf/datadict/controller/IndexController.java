package com.jf.datadict.controller;

import com.jf.datadict.model.JSONResult;
import com.jf.datadict.model.MySqlVO;
import com.jf.datadict.service.CustomService;
import com.jf.datadict.service.DataStatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
public class IndexController {

    @Resource
    private CustomService customService;

    @Resource
    private DataStatisticsService dataStatisticsService;

    @GetMapping("/")
    public String index(){
        return "index";
    }

    @ResponseBody
    @PostMapping("/validauteMySql")
    public JSONResult validauteMySqlConnection(@RequestBody MySqlVO vo) {
        return customService.validauteMySqlConnection(vo);
    }

    @ResponseBody
    @GetMapping("/queryDataStatistics")
    public JSONResult queryDataStatistics() {
        return dataStatisticsService.queryDataStatistics();
    }
}
