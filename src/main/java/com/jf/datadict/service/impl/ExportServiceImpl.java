package com.jf.datadict.service.impl;

import com.jf.datadict.entity.DictTableStructure;
import com.jf.datadict.model.JSONResult;
import com.jf.datadict.model.MySqlTable;
import com.jf.datadict.service.DetailService;
import com.jf.datadict.service.ExportService;
import com.jf.datadict.util.WordKit;
import com.jf.datadict.util.MyStringUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class ExportServiceImpl implements ExportService {

    @Resource
    private DetailService detailService;

    @Override
    public JSONResult Mysql2Word(HttpSession session, String dataBaseName, String tableName) {
        if (MyStringUtil.isEmpty(dataBaseName)) {
            return JSONResult.build(100, "dbName不可为空");
        }
        JSONResult rs = detailService.queryTableStructure(session, dataBaseName, tableName);
        List<DictTableStructure> dtsList = (List<DictTableStructure>) rs.getData();

        List<MySqlTable> tableList = new ArrayList<>();
        Map<String, MySqlTable> tMap = new HashMap<>();
        for (DictTableStructure dts : dtsList) {
            // 判断表名是否存在，存在将表字段赋值进集合
            MySqlTable m;
            List<String[]> fieldList;
            if (tMap.containsKey(dts.getTableName())) {
                m = tMap.get(dts.getTableName());
                fieldList = m.getFieldList();
            } else {
                m = new MySqlTable();
                m.setDbName("第一章 "+ StringUtils.upperCase(dataBaseName));
                if (MyStringUtil.isEmpty(dts.getTableChName())) {
                    m.setTitle(dts.getTableName());
                } else {
                    m.setTitle(dts.getTableChName()+dts.getTableName());
                }
                m.setTableName(dts.getTableName());
                m.setTableChName(dts.getTableChName());
                m.setFieldCount(dts.getFieldCount());
                fieldList = new ArrayList<>();
                tableList.add(m);
            }
            String[] s = new String[7];
            s[0] = dts.getField();
            s[1] = dts.getType();
            s[2] = dts.getDefaultValue();
            s[3] = dts.getKey();
            s[4] = dts.getLength();
            s[5] = dts.getIsNull();
            s[6] = dts.getComment();
            fieldList.add(s);
            m.setFieldList(fieldList);
            tMap.put(dts.getTableName(), m);
        }

        // 获取当前pc端访问的用户名
        String currentUser = System.getProperty("user.name");
        // 获取当前日期
        SimpleDateFormat sf = new SimpleDateFormat("yyyyMMdd");
        String currentDate = sf.format(new Date());
        // 导出文件的存放地址
        String fileName = "数据字典_"+dataBaseName+"_"+currentDate+".doc";
        String exportPath = "C:/Users/"+currentUser+"/Desktop/"+fileName;
        WordKit wordKit = new WordKit();
        try {
            wordKit.writeTableToWord(tableList, exportPath);
        } catch (Exception e) {
            e.printStackTrace();
            return JSONResult.error500("导出失败");
        }
        return JSONResult.ok(fileName);
    }
}
