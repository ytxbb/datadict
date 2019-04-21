package com.jf.datadict.service;

import com.jf.datadict.model.JSONResult;
import com.jf.datadict.model.MySqlVO;

public interface CustomService {

    JSONResult queryAllDataBaseOfCustom();

    JSONResult validauteMySqlConnection(MySqlVO vo);

    JSONResult queryMenuList(String dbName);

    JSONResult queryTableStructure();
}
