package com.jf.datadict.service;

import com.jf.datadict.model.JSONResult;
import com.jf.datadict.model.MongoDBVO;
import com.jf.datadict.model.MySqlVO;

import javax.servlet.http.HttpSession;

public interface CustomService {

    JSONResult queryAllDBOfCustom(HttpSession session);

    JSONResult validateMySqlConnection(MySqlVO vo);

    JSONResult validateMongoDBConnection(MongoDBVO vo);

    JSONResult queryMenuList(HttpSession session, String dbName);

    JSONResult queryTableStructure();
}
