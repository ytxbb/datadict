package com.jf.datadict.controller;

import com.jf.datadict.constants.StaticMySqlQuery;
import com.jf.datadict.model.JSONResult;
import com.jf.datadict.model.MongoDBVO;
import com.jf.datadict.model.MySqlVO;
import com.jf.datadict.service.CustomService;
import com.jf.datadict.service.DataStatisticsService;
import com.jf.datadict.util.MyStringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

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

    @RequestMapping("/show")
    public ModelAndView show(HttpSession httpSession, ModelAndView mv, @RequestParam("db") String dbName,
                             @RequestParam(value = "t", required = false) String tableName) {
        if (httpSession.getAttribute("url") == null) {
            mv.setViewName("redirect:/");
        } else {
            httpSession.setAttribute("dbName", dbName);
            httpSession.setAttribute("tableName", tableName);
            mv.setViewName("show");
        }
        return mv;
    }

    @RequestMapping("/costomShow")
    public ModelAndView costomShow(HttpSession httpSession, ModelAndView mv, MySqlVO vo) {
        if (httpSession.getAttribute("url") != null) {
            mv.setViewName("customIndex");
            return mv;
        }
        if (MyStringUtil.isEmpty(vo.getIp())) {
            mv.setViewName("redirect:/");
        } else {
            if (httpSession.getAttribute("url") == null) {
                String url = StaticMySqlQuery.getMysqlUrl(vo);
                httpSession.setAttribute("url", url);
                httpSession.setAttribute("username", vo.getUserName());
                httpSession.setAttribute("password", vo.getPassword());
            }
            mv.setViewName("customIndex");
        }
        return mv;
    }

    @ResponseBody
    @PostMapping("/validateMySql")
    public JSONResult validateMySqlConnection(@RequestBody MySqlVO vo) {
        return customService.validateMySqlConnection(vo);
    }

    @ResponseBody
    @PostMapping("/validateMongodb")
    public JSONResult validauteMongodb(@RequestBody MongoDBVO vo) {
        return customService.validateMongoDBConnection(vo);
    }

    @ResponseBody
    @GetMapping("/queryDataStatistics")
    public JSONResult queryDataStatistics() {
        return dataStatisticsService.queryDataStatistics();
    }
}
