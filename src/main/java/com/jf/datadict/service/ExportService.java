package com.jf.datadict.service;

import com.jf.datadict.model.JSONResult;

import javax.servlet.http.HttpSession;

public interface ExportService {

    JSONResult Mysql2Word(HttpSession session, String dataBaseName, String tableName);
}
