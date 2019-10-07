package com.jf.datadict.constants;

import org.apache.poi.xwpf.usermodel.ParagraphAlignment;

public abstract class WordConfig {
	protected final String TITLE_LEVEL_ONE = "标题 1";
	protected final String TITLE_LEVEL_TWO = "标题 2";
	protected final String TITLE_LEVEL_THREE = "标题 3";

	//表目录宽度
	protected final int ROWCEL_MENU_INDEX_WIDTH = 1000;// 序号的宽度
	protected final int ROWCEL_MENU_NAME_WIDTH = 3000;// 表名的宽度
	protected final int ROWCEL_MENU_CHNAME_WIDTH = 4800;// 表名说明的宽度
	//注释那一列的宽度
	protected final int ROW_CEL_WIDTH_REMARK = 2000;// 注释那一列的宽度
	
	// 表格第一行相关设置
	protected final String FIRST_ROW_COLOR = "D3D3D3";// 表格第一行背景色
	protected final boolean IS_FIRST_ROW_COLOR = true;// 是否显示表格背景色
	protected final int FIRST_ROW_HEIGHT = 500;// 表格第一行高度
	protected final int FIRST_ROW_CEL_WIDTH = 900;// 表格第一行中单元格的宽度
	protected final int FIRST_ROW_FONT_SIZE = 11;// 单元格中文字大小
	protected final String FIRST_ROW_FONT_FAMILY = "微软雅黑";// 单元格中文字字体
	protected final boolean FIRST_ROW_FONT_BOLD = true;// 单元格中文字是否加粗
	protected final String FIRST_ROW_FONT_COLOR = "000000";// 单元格中文字颜色
	protected final ParagraphAlignment FIRST_ROW_ALIGNMENT = ParagraphAlignment.LEFT;// 设置表格文字对齐方式

	// 表格除了第一行，其他行相关设置
	protected final String ROW_COLOR = "EEEEFF";// （除了第一行外）表格内容行背景色
	protected final boolean IS_ROW_COLOR = false;// 是否显示表格背景色
	protected final int ROW_HEIGHT = 380;// 表格内容行高度
	protected final int ROW_CEL_WIDTH = 900;// 表格内容行中单元格的宽度
	protected final int ROW_FONT_SIZE = 9;// 单元格中文字大小
	protected final String ROW_FONT_FAMILY = "微软雅黑";// 单元格中文字字体
	protected final boolean ROW_FONT_BOLD = true;// 单元格中文字是否加粗
	protected final boolean ROW_FONT_BOLD_OF_MENU = false;// 表目录中的单元格中文字是否加粗
	protected final String ROW_FONT_COLOR = "000000";// 单元格中文字颜色
	protected final ParagraphAlignment ROW_ALIGNMENT = ParagraphAlignment.LEFT;// 设置表格文字对齐方式

	// 表格标题相关设置
	protected final int TITLE_FONT_SIZE = 14;// 表格标题文字大小
	protected final String TITLE_FONT_FAMILY = "宋体";// 表格标题文字字体
	protected final boolean TITLE_FONT_BOLD = true;// 表格标题文字是否加粗
	protected final boolean TITLE_ADD_INDEX = true;// 表格标题是否自动加上序号
	protected final ParagraphAlignment TITLE_ALIGNMENT = ParagraphAlignment.LEFT;// 设置表格文字对齐方式
	protected final boolean IS_RETURN_ROW = false;// 是否换行

	// 表格主体相关设置
	protected final String TABLE_WIDTH = "8800";// 表格宽度

}
