package com.jf.datadict.controller;

import com.jf.datadict.model.JSONResult;
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

    @ResponseBody
    @PostMapping("/queryMenuList")
    public JSONResult queryMenuList(HttpSession session, String dbName) {
        return detailService.queryMenuList(session, dbName);
    }

    @ResponseBody
    @PostMapping("/queryTableStructure")
    public JSONResult queryTableStructure(@RequestParam("db_name") String dataBaseName,
                                          @RequestParam("table_name") String tableName,
                                          HttpSession httpSession) {
        return detailService.queryTableStructure(httpSession, dataBaseName, tableName);
    }

    @ResponseBody
    @PostMapping("/exportWord")
    public JSONResult exportWord(@RequestParam("db_name") String dataBaseName,
                                          @RequestParam("table_name") String tableName,
                                          HttpSession httpSession) {
        return detailService.queryTableStructure(httpSession, dataBaseName, tableName);
    }
}
