package com.jf.datadict.controller;

import com.jf.datadict.constants.StaticMySqlQuery;
import com.jf.datadict.model.JSONResult;
import com.jf.datadict.model.MySqlVO;
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
}
