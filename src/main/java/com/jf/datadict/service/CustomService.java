package com.jf.datadict.service;

import com.jf.datadict.model.DataBaseVO;
import com.jf.datadict.model.JSONResult;

import javax.servlet.http.HttpSession;

public interface CustomService {

    JSONResult queryAllDBOfCustom(HttpSession session);

    JSONResult validateMySqlConnection(DataBaseVO vo);

    JSONResult validateMongoDBConnection(DataBaseVO vo);

    JSONResult queryMenuList(HttpSession session, String dbName);

    JSONResult queryTableStructure();
}
