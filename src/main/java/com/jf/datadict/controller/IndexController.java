package com.jf.datadict.controller;

import com.jf.datadict.constants.StaticMySqlQuery;
import com.jf.datadict.model.JSONResult;
import com.jf.datadict.model.MySqlVO;
import com.jf.datadict.service.CustomService;
import com.jf.datadict.service.DataStatisticsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

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

    @PostMapping("/show")
    public String show(HttpSession httpSession, @RequestParam("dbName") String dbName, @RequestParam("tableName") String tableName) {
        httpSession.setAttribute("dbName", dbName);
        httpSession.setAttribute("tableName", tableName);
        return "show";
    }

    @PostMapping("/costomShow")
    public String costomShow(HttpSession httpSession, MySqlVO vo) {
        String url = StaticMySqlQuery.getMysqlUrl(vo);
        if (url == null) {
            return "index";
        }
        httpSession.setAttribute("url", url);
        httpSession.setAttribute("username", vo.getUserName());
        httpSession.setAttribute("password", vo.getPassword());
        return "customIndex";
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
