package com.jf.datadict.service.impl;

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
}
