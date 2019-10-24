package com.jf.datadict.controller;

import com.jf.datadict.model.JSONResult;
import com.jf.datadict.model.MySqlTable;
import com.jf.datadict.service.DetailService;
import com.jf.datadict.service.ExportService;
import com.jf.datadict.util.WordKit;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
public class DetailController {

    @Resource
    private DetailService detailService;

    @Resource
    private ExportService exportService;

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
        return JSONResult.ok(exportService.Mysql2Word(httpSession, dataBaseName, tableName));
    }

    @RequestMapping(value = "/exportWord3",method = RequestMethod.GET)
    public void exportExcel(HttpServletResponse response,@RequestParam("db") String dataBaseName,
                            @RequestParam("t") String tableName,
                            HttpSession httpSession) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        WordKit wordKit = new WordKit();

        List<MySqlTable> tableList = exportService.Mysql2Word(httpSession, dataBaseName, tableName);
        wordKit.writeTableToWord(tableList, os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);

        // 获取当前日期
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sf.format(new Date());
        // 导出文件的存放地址
        String fileName = "数据字典_"+dataBaseName+"_"+currentDate+".doc";
        try {
            //清空缓存
            response.reset();
            //定义下载的类型，标明是word文件
            response.setContentType("application/msword;charset=UTF-8");
            // 定义浏览器响应表头，并定义下载名
            response.setHeader("Content-disposition", "attachment;filename="+ URLEncoder.encode(fileName,"UTF8"));
            response.setContentLength(content.length);

            // 第一种
            /*byte[] buffer = new byte[is.available()];
            ServletOutputStream output = response.getOutputStream();
            output.write(buffer);
            output.close();
            is.close();*/

            // 使用缓冲输入输出流
            ServletOutputStream servletOut = response.getOutputStream();
            BufferedInputStream bis = new BufferedInputStream(is);
            BufferedOutputStream bos = new BufferedOutputStream(servletOut);

            byte[] buff = new byte[is.available()];
            int bytesRead;
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
            // 关闭流
            os.close();
            is.close();
            bis.close();
            bos.close();
            servletOut.flush();
            servletOut.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
