package com.jf.datadict.service.impl;

import com.jf.datadict.entity.DictMenu;
import com.jf.datadict.entity.DictTableStructure;
import com.jf.datadict.exception.ServiceException;
import com.jf.datadict.model.JSONResult;
import com.jf.datadict.service.CustomService;
import com.jf.datadict.util.DBUtils;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Service
public class customServiceImpl implements CustomService {

    @Override
    public JSONResult queryAllDataBaseOfCustom() {
        List<String> dataBaseNames = new ArrayList<>();
        try {
            ResultSet rs = DBUtils.query("select schema_name db_name from information_schema.schemata");
            while (rs.next()){
                dataBaseNames.add(rs.getString(1));

            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return JSONResult.ok(dataBaseNames);
    }

    @Override
    public JSONResult queryMenuList(String dbName) {
        List<DictMenu> resMenuList = new ArrayList<>();

        String sql = "select table_name,count(table_name) c from information_schema.columns where table_schema ="+dbName+" group by table_name";
        try {
            ResultSet rs = DBUtils.query(sql);
            while (rs.next()){
                DictMenu menu = new DictMenu();

                int parentUid = (int) (Math.random()*(600-100));
                menu.setParentUid(parentUid);
                menu.setMenuName(rs.getString(1));
                resMenuList.add(menu);
            }
        } catch (Exception e){
            e.printStackTrace();
            throw new ServiceException("查询菜单时出错："+e.getMessage());
        }

        return JSONResult.ok(resMenuList);
    }

    @Override
    public JSONResult queryTableStructure() {
        return null;
    }
}
