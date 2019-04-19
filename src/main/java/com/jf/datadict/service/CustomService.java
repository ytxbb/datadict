package com.jf.datadict.service;

import com.jf.datadict.model.JSONResult;

public interface CustomService {

    JSONResult queryAllDataBaseOfCustom();

    JSONResult queryMenuList(String dbName);

    JSONResult queryTableStructure();
}
