package com.jf.datadict.controller;

import com.jf.datadict.constants.StaticConstants;
import com.jf.datadict.exception.ServiceException;
import com.jf.datadict.model.JSONResult;
import com.jf.datadict.model.MySqlVO;
import com.jf.datadict.service.CustomService;
import com.jf.datadict.service.DetailService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

@Controller
public class DetailController {

    @Resource
    private DetailService detailService;

    @Resource
    private CustomService customService;

    @PostMapping("/show")
    public String show(HttpSession httpSession, @RequestParam("dbName") String dbName, @RequestParam("tableName") String tableName) {
        httpSession.setAttribute("dbName", dbName);
        httpSession.setAttribute("tableName", tableName);
        return "show";
    }

    @PostMapping("/costomShow")
    public String costomShow(HttpSession httpSession, MySqlVO vo) {
        String url = "jdbc:mysql://" + vo.getIp() + ":" + vo.getPort() + "/mysql?useSSL=false&useUnicode=true&characterEncoding=utf-8&autoReconnect=true&failOverReadOnly=false";
        StaticConstants.DB_MYSQL_MAP.put("url", url);
        StaticConstants.DB_MYSQL_MAP.put("username", vo.getUserName());
        StaticConstants.DB_MYSQL_MAP.put("password", vo.getPwd());
        httpSession.setAttribute("back_url", url);
        return "customIndex";
    }

    @ResponseBody
    @PostMapping("/queryMenuList")
    public JSONResult queryMenuList(String dbName) {
        return detailService.queryMenuList(dbName);
    }

    @ResponseBody
    @PostMapping("/queryTableStructure")
    public JSONResult queryTableStructure(@RequestParam("db_name") String dataBaseName,
                                          @RequestParam("table_name") String tableName) {
        return detailService.queryTableStructure(dataBaseName, tableName);
    }
}
