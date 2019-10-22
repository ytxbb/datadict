package com.jf.datadict.service;

import com.jf.datadict.model.MySqlTable;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface ExportService {

    List<MySqlTable> Mysql2Word(HttpSession session, String dataBaseName, String tableName);
}
