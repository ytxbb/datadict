package com.jf.datadict.test;

import com.alibaba.fastjson.JSONArray;
import com.jf.datadict.entity.DictTableStructure;
import com.jf.datadict.model.MySqlTable;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class Mysql2WordKit {
	private final static boolean isDebug = false;

	public static void main(String[] arg) throws Exception {
		long startTime = System.currentTimeMillis();
		log.info("starting to export mysql table info....");
//		List<MySqlTable> tableList = MysqlKit.getTableInfos();
//		if (isDebug) {
//			MysqlKit.consoleInfo();// 输出表格信息
//		}
        String str = "";
		List<DictTableStructure> dtsList = JSONArray.parseArray(str, DictTableStructure.class);

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
				m.setTitle(dts.getTableName()+"("+dts.getTableChName()+")");
				m.setTableName(dts.getTableName());
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

		log.info("获取mysql 表格结构成功...");
		log.info("开始将mysql表结果信息写入word文档中....");
		WordKit wordKit = new WordKit();
		wordKit.writeTableToWord(tableList);
		log.info("export successfully!....");
		log.debug("本次导出总耗时：" + (System.currentTimeMillis() - startTime)
				/ 1000 + " s");
	}

}
