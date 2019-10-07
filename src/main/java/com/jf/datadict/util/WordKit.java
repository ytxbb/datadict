package com.jf.datadict.util;

import com.jf.datadict.constants.WordConfig;
import com.jf.datadict.model.MySqlTable;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;

import java.io.FileOutputStream;
import java.math.BigInteger;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 参考来源：https://github.com/cpthack/mysql2word.git
 */
public class WordKit extends WordConfig {

    private static final int celMenuNum = 3;
    private static final int celNum = 7;
    private static final ArrayList<String> tableMenuRelation = new ArrayList<String>() {
        {
            this.add("序号");
            this.add("表名");
            this.add("说明");
        }
    };
    private static final ArrayList<String> tableRelation = new ArrayList<String>() {
        {
            this.add("字段名称");
            this.add("字段类型");
            this.add("默认值");
            this.add("主键");
            this.add("字段长度");
            this.add("是否为空");
            this.add("备注信息");
        }
    };

    public void writeTableToWord(List<MySqlTable> tableList) throws Exception {
        XWPFDocument document = new XWPFDocument();
        // 设置封面
        setWordFirstPage(document);
        // 设置表目录
        setTableMenuPage(document, tableList);

        boolean isSetedTitle = false;
        for (MySqlTable mysqlTable : tableList) {
            if (TITLE_ADD_INDEX) {
                int index = tableList.indexOf(mysqlTable) + 1;
                mysqlTable.setTitle(index + ". " + mysqlTable.getTitle());
            }
            createSimpleTableNormal(document, mysqlTable, isSetedTitle);
            isSetedTitle = true;
        }
        saveDocument(document, EXPORT_FILE_PATH);
    }

    // 往word插入一张表格
    private void createSimpleTableNormal(XWPFDocument document, MySqlTable mysqlTable, boolean isSetedTitle) {
        if (!isSetedTitle) {
            // 设置一级标题
            setTableTitle(document, TITLE_LEVEL_ONE, 1, mysqlTable.getDbName());
        }
        // 设置标题
        setTableTitle(document, TITLE_LEVEL_TWO, 2, mysqlTable.getTitle());
        // 创建表格
        XWPFTable table = createTable(document, mysqlTable.getFieldList().size(), celNum);
        // 设置表格中行列内容
        setRowText(table, mysqlTable);
        // 往表格中插入第一列标题内容
        setFirstRowText(table, mysqlTable);
    }

    // 设置封面
    private void setWordFirstPage(XWPFDocument document) {
        // 创建paragraph对象,设置对其方式为中间对齐，行间距为1
        XWPFParagraph paragraph = document.createParagraph();
        paragraph.setAlignment(ParagraphAlignment.CENTER);
        paragraph.setSpacingBetween(1);

        //创建操作对象run,设置字体为微软雅黑，字号24，加粗
        XWPFRun run = paragraph.createRun();
        run.setFontFamily("微软雅黑");
        run.setFontSize(32);
        run.setBold(true);

        // 空出一行
        run.addBreak();
        run.setText("数据字典");
        run.addBreak();

        run = paragraph.createRun();
        run.setFontFamily("微软雅黑");
        run.setFontSize(20);
        run.setBold(true);
        run.setText("版本-v3.12");
        run.addBreak();

        //空出十行
        for (int i = 0; i < 8; i++) {
            run.addBreak();
        }

        //需要重新create一个XWPFRun对象，如果这里没有重新创建，那么上面标题的样式，也会改变成下面代码设置的样式。
        run = paragraph.createRun();
        run.setBold(false);
        run.setFontSize(16);
        run.setFontFamily("宋体");

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sf.format(new Date());
        run.setText(Date2ChineseDate.toChinese(currentDate));

        addNewPage(document, BreakType.PAGE);
    }

    // 设置表目录
    private void setTableMenuPage(XWPFDocument document, List<MySqlTable> tableList) {
        // 设置标题
        setTableTitle(document, TITLE_LEVEL_TWO, 2, "表目录");
        // 遍历所有表名和表名注释
        LinkedHashMap<String, String> mtsMap = new LinkedHashMap<>();
        for (MySqlTable mt : tableList) {
            mtsMap.put(mt.getTableName(), mt.getTableChName());
        }
        // 创建表格，表目录3列
        XWPFTable table = createTable(document, mtsMap.size(), celMenuNum);
        // 设置表格中行列内容
        setRowTextOfMenu(table, mtsMap);
        // 往表格中插入第一列标题内容
        setFirstRowTextOfMenuList(table);
        // 添加新页
        addNewPage(document, BreakType.PAGE);
    }

    // 创建表格
    private XWPFTable createTable(XWPFDocument document, int rowNum, int celNum) {
        XWPFTable table = document.createTable(rowNum, celNum);
        CTTbl ttbl = table.getCTTbl();
        CTTblPr tblPr = ttbl.getTblPr() == null ? ttbl.addNewTblPr() : ttbl
                .getTblPr();
        CTTblWidth tblWidth = tblPr.isSetTblW() ? tblPr.getTblW() : tblPr
                .addNewTblW();
        CTJc cTJc = tblPr.addNewJc();
        cTJc.setVal(STJc.Enum.forString("center"));
        tblWidth.setW(new BigInteger(TABLE_WIDTH));
        tblWidth.setType(STTblWidth.DXA);
        return table;
    }

    // 设置表格标题
    private void setTableTitle(XWPFDocument document, String strStyleId, int headingLevel, String tableTitle) {
        XWPFParagraph p2 = document.createParagraph();

        addCustomHeadingStyle(document, strStyleId, headingLevel);

        p2.setAlignment(TITLE_ALIGNMENT);
        XWPFRun r2 = p2.createRun();
        p2.setStyle(strStyleId);
        r2.setTextPosition(5);
        r2.setText(tableTitle);
        if (TITLE_FONT_BOLD) {
            r2.setBold(TITLE_FONT_BOLD);
        }
        r2.setFontFamily(TITLE_FONT_FAMILY);
        r2.setFontSize(TITLE_FONT_SIZE);
        if (IS_RETURN_ROW) {
            r2.addCarriageReturn();// 是否换行
        }
    }

    // 合并列
    private void mergeCellsHorizontal(XWPFTable table, int row, int fromCell, int toCell) {
        for (int cellIndex = fromCell; cellIndex <= toCell; cellIndex++) {
            XWPFTableCell cell = table.getRow(row).getCell(cellIndex);
            if (cellIndex == fromCell) {
                // The first merged cell is set with RESTART merge value
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.RESTART);
            } else {
                // Cells which join (merge) the first one, are set with CONTINUE
                cell.getCTTc().addNewTcPr().addNewHMerge().setVal(STMerge.CONTINUE);
            }
        }
    }

    // 设置表格第一行内容
    private void setFirstRowText(XWPFTable table, MySqlTable mysqlTable) {
        XWPFTableRow firstRow;
        XWPFTableCell firstCell;
        firstRow = table.insertNewTableRow(0);
        firstRow.setHeight(FIRST_ROW_HEIGHT);

        XWPFTableRow firstRow0;
        XWPFTableCell firstCell0;
        firstRow0 = table.insertNewTableRow(0);
        firstRow0.setHeight(FIRST_ROW_HEIGHT);
        for (int i = 0; i < celNum; i++) {
            firstCell0 = firstRow0.addNewTableCell();
            createVSpanCell(firstCell0, mysqlTable.getTableName(), FIRST_ROW_COLOR,
                    FIRST_ROW_CEL_WIDTH, STMerge.RESTART);
        }
        mergeCellsHorizontal(table, 0, 0, 6);
        // 表关系列
        for (String fieldValue : tableRelation) {
            firstCell = firstRow.addNewTableCell();
            createVSpanCell(firstCell, fieldValue, FIRST_ROW_COLOR,
                    FIRST_ROW_CEL_WIDTH, STMerge.RESTART);
        }
    }

    // 设置表目录表格第一行内容
    private void setFirstRowTextOfMenuList(XWPFTable table) {
        XWPFTableRow firstRow;
        XWPFTableCell firstCell;
        firstRow = table.insertNewTableRow(0);
        firstRow.setHeight(FIRST_ROW_HEIGHT);

        int size = tableMenuRelation.size();
        for (int i = 0; i < size; i++) {
            firstCell = firstRow.addNewTableCell();
            int width;
            switch (i) {
                case '0':
                    width = ROWCEL_MENU_INDEX_WIDTH;
                    break;
                case '1':
                    width = ROWCEL_MENU_NAME_WIDTH;
                    break;
                default:
                    width = ROWCEL_MENU_CHNAME_WIDTH;
                    break;
            }
            createVSpanCell(firstCell, tableMenuRelation.get(i), FIRST_ROW_COLOR,
                    width, STMerge.RESTART);
        }
    }

    // 设置每行的内容
    private void setRowText(XWPFTable table, MySqlTable mysqlTable) {
        XWPFTableRow firstRow;
        XWPFTableCell firstCell;

        List<String[]> fieldList = mysqlTable.getFieldList();
        String[] fieldValues;
        for (int i = 0, fieldListSize = fieldList.size(); i < fieldListSize; i++) {
            firstRow = table.getRow(i);
            firstRow.setHeight(ROW_HEIGHT);
            fieldValues = fieldList.get(i);
            for (int j = 0, fieldValuesSize = fieldValues.length; j < fieldValuesSize; j++) {
                firstCell = firstRow.getCell(j);
                if (j == (fieldValuesSize - 1)) {
                    setCellText(firstCell, fieldValues[j], ROW_COLOR, ROW_CEL_WIDTH_REMARK);
                } else {
                    setCellText(firstCell, fieldValues[j], ROW_COLOR, ROW_CEL_WIDTH);
                }
            }
        }
    }

    // 设置每行的内容
    private void setRowTextOfMenu(XWPFTable table, Map<String, String> mtsMap) {
        XWPFTableRow firstRow;
        XWPFTableCell firstCell;

        int size = mtsMap.keySet().size();
        ArrayList<String> tNameList = new ArrayList<>(mtsMap.keySet());

        for (int i = 0; i < size; i++) {
            firstRow = table.getRow(i);
            firstRow.setHeight(ROW_HEIGHT);
            for (int j = 0; j < celMenuNum; j++) {
                firstCell = firstRow.getCell(j);

                switch (j) {
                    case 0:
                        setCellText(firstCell, (i+1)+"", ROW_COLOR, ROWCEL_MENU_INDEX_WIDTH);
                        break;
                    case 1:
                        setCellText(firstCell, tNameList.get(i), ROW_COLOR, ROWCEL_MENU_NAME_WIDTH);
                        break;
                    default:
                        setCellText(firstCell, mtsMap.get(tNameList.get(i)), ROW_COLOR, ROWCEL_MENU_CHNAME_WIDTH);
                        break;
                }
            }
        }
    }

    // 添加单个列的内容
    private void setCellText(XWPFTableCell cell, String value, String bgcolor,
                             int width) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));

        if (IS_ROW_COLOR)// 设置颜色
            cell.setColor(bgcolor);

        XWPFParagraph p = getXWPFParagraph(ROW_ALIGNMENT, ROW_FONT_BOLD,
                ROW_FONT_FAMILY, ROW_FONT_SIZE, ROW_FONT_COLOR, value);
        cell.setParagraph(p);
    }

    // 往第一行插入一列
    private void createVSpanCell(XWPFTableCell cell, String value,
                                 String bgcolor, int width, STMerge.Enum stMerge) {
        CTTc cttc = cell.getCTTc();
        CTTcPr cellPr = cttc.addNewTcPr();
        cellPr.addNewTcW().setW(BigInteger.valueOf(width));
        if (IS_FIRST_ROW_COLOR)
            cell.setColor(bgcolor);

        XWPFParagraph p = getXWPFParagraph(FIRST_ROW_ALIGNMENT,
                FIRST_ROW_FONT_BOLD, FIRST_ROW_FONT_FAMILY,
                FIRST_ROW_FONT_SIZE, FIRST_ROW_FONT_COLOR, value);
        cell.setParagraph(p);
    }

    private XWPFParagraph getXWPFParagraph(ParagraphAlignment alignment,
                                           boolean isBold, String fontFamily, int fontSize, String fontColor,
                                           String celValue) {
        XWPFDocument doc = new XWPFDocument();
        XWPFParagraph p = doc.createParagraph();
        XWPFRun r2 = p.createRun();
        p.setAlignment(alignment);
        if (isBold)
            r2.setBold(isBold);
        r2.setFontFamily(fontFamily);
        r2.setFontSize(fontSize);
        r2.setText(celValue);
        r2.setColor(fontColor);
        return p;
    }

    // 添加新的一个文档
    private void addNewPage(XWPFDocument document, BreakType breakType) {
        XWPFParagraph xp = document.createParagraph();
        xp.createRun().addBreak(breakType);
    }

    // 输出文件
    private void saveDocument(XWPFDocument document, String savePath) throws Exception {
        FileOutputStream fos = new FileOutputStream(savePath);
        document.write(fos);
        fos.close();
        document.close();
    }

    /**
     * 增加自定义标题样式。这里用的是stackoverflow的源码
     *
     * @param docxDocument 目标文档
     * @param strStyleId   样式名称
     * @param headingLevel 样式级别
     */
    private static void addCustomHeadingStyle(XWPFDocument docxDocument, String strStyleId, int headingLevel) {

        CTStyle ctStyle = CTStyle.Factory.newInstance();
        ctStyle.setStyleId(strStyleId);

        CTString styleName = CTString.Factory.newInstance();
        styleName.setVal(strStyleId);
        ctStyle.setName(styleName);

        CTDecimalNumber indentNumber = CTDecimalNumber.Factory.newInstance();
        indentNumber.setVal(BigInteger.valueOf(headingLevel));

        // lower number > style is more prominent in the formats bar
        ctStyle.setUiPriority(indentNumber);

        CTOnOff onoffnull = CTOnOff.Factory.newInstance();
        ctStyle.setUnhideWhenUsed(onoffnull);

        // style shows up in the formats bar
        ctStyle.setQFormat(onoffnull);

        // style defines a heading of the given level
        CTPPr ppr = CTPPr.Factory.newInstance();
        ppr.setOutlineLvl(indentNumber);
        ctStyle.setPPr(ppr);

        XWPFStyle style = new XWPFStyle(ctStyle);

        // is a null op if already defined
        XWPFStyles styles = docxDocument.createStyles();

        style.setType(STStyleType.PARAGRAPH);
        styles.addStyle(style);

    }

}
