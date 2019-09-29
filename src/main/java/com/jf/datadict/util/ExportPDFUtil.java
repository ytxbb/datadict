package com.jf.datadict.util;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;

import java.io.FileOutputStream;

public class ExportPDFUtil {

    public static void main(String[] args) {
        String path = "C:\\Users\\xyt\\Desktop\\test.pdf";
        exportPDF(path);
    }

    public static void exportPDF(String path){
        String pdfDesc = "考务综合管理平台V3.10-标准化考点管理系统";
        try {
            // 第一步，实例化一个document对象
            Document document = new Document(PageSize.A4);
            // 第二步，设置要导出的路径
            // FileOutputStream out = new  FileOutputStream("H:/workbook111.pdf");
            FileOutputStream out = new  FileOutputStream(path);
            //如果是浏览器通过request请求需要在浏览器中输出则使用下面方式
            //OutputStream out = response.getOutputStream();
            // 第三步,设置字符
            BaseFont bfChinese = BaseFont.createFont(getCurrentOperatingSystemFontPath(), BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
//            BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED);
            Font fontZH = new Font(bfChinese, 12.0F, 0);
            // 第四步，将pdf文件输出到磁盘
            PdfWriter writer = PdfWriter.getInstance(document, out);
            // 第五步，打开生成的pdf文件
            document.open();
            // 第六步,设置内容
            // 纯文本的设置
            Anchor anchorHeadTitle = new Anchor(pdfDesc, fontZH);
            anchorHeadTitle.setName("BackToTop");
            anchorHeadTitle.setLeading(2);
            Anchor anchorHeadSubTitle = new Anchor("test_dict数据字典", fontZH);

            Paragraph headParagraph = new Paragraph();
            headParagraph.setSpacingBefore(50);
            headParagraph.add(anchorHeadTitle);
            headParagraph.add(anchorHeadSubTitle);

            document.add(headParagraph);
            document.add(new Paragraph("版本v1.80",
                    FontFactory.getFont(FontFactory.COURIER,14,Font.BOLD,
                            new CMYKColor(0,255,0,0))));
            // 章节的设置
            Paragraph titleFirest = new Paragraph("第一章 (MySql)JF_VIS_DB",
                    FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC,
                            new CMYKColor(0, 255, 255,17)));
            Chapter chapterFirst = new Chapter(titleFirest, 1);
            chapterFirst.setNumberDepth(0);
            // 小标题的设置
            Paragraph title11 = new Paragraph("1.1\t表目录",
                    FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,
                            new CMYKColor(0, 255, 255,17)));
            Section section1 = chapterFirst.addSection(title11);
            Paragraph title12 = new Paragraph("1.2\t考试计划设置",
                    FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,
                            new CMYKColor(0, 255, 255,17)));
            Section section2 = chapterFirst.addSection(title12);
            Paragraph someSectionText = new Paragraph("1.2.1\t考试计划表p_examinfo", fontZH);
            section2.add(someSectionText);
            someSectionText = new Paragraph("Following is a 4 X 2 table.");
            section2.add(someSectionText);


            document.add(chapterFirst);

            String title = "导出pdf测试的情况";
            document.add(new Paragraph(new Chunk(title, fontZH).setLocalDestination(title)));
            document.add(new Paragraph("\n"));
            // 创建table,注意这里的2是两列的意思,下面通过table.addCell添加的时候必须添加整行内容的所有列
            PdfPTable table = new PdfPTable(4);
            table.setSpacingBefore(25);
            table.setSpacingAfter(25);
            // table.setWidthPercentage(100.0F);
            PdfPCell c1 = new PdfPCell(new Phrase("字段名", fontZH));
            table.addCell(c1);
            PdfPCell c2 = new PdfPCell(new Phrase("数据类型", fontZH));
            table.addCell(c2);
            PdfPCell c3 = new PdfPCell(new Phrase("长度", fontZH));
            table.addCell(c3);
            PdfPCell c4 = new PdfPCell(new Phrase("键值", fontZH));
            table.addCell(c4);
            PdfPCell c5 = new PdfPCell(new Phrase("是否可空", fontZH));
            table.addCell(c5);
            PdfPCell c6 = new PdfPCell(new Phrase("默认值", fontZH));
            table.addCell(c6);
            PdfPCell c7 = new PdfPCell(new Phrase("描述", fontZH));
            table.addCell(c7);
            table.addCell("1.1");
            table.addCell("1.2");
            table.addCell("1.3");
            table.addCell("1.5");
            table.addCell("1.6");
            table.addCell("1.7");
            section2.add(table);

            document.add(new Paragraph("\t\t\t下面是列表对象", fontZH));

            // 列表对象
            List l = new List(true, false, 10);
            l.add(new ListItem("First item of list"));
            l.add(new ListItem("Second item of list"));
            section2.add(l);

            Paragraph title2 = new Paragraph("Using Anchor",
                    FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,
                            new CMYKColor(0, 255, 0, 0)));
            section2.add(title2);
            title2.setSpacingBefore(100);
            Anchor anchor2 = new Anchor("Back To Top");
            anchor2.setReference("#BackToTop");
            section2.add(anchor2);

            //第一列是列表名
            /*table.setHeaderRows(1);
            table.getDefaultCell().setHorizontalAlignment(1);
            table.addCell(new Paragraph("序号", fontZH));
            table.addCell(new Paragraph("性别", fontZH));
            table.addCell(new Paragraph("姓名", fontZH));
            table.addCell(new Paragraph("年龄", fontZH));
            table.addCell(new Paragraph("1", fontZH));
            table.addCell(new Paragraph("男", fontZH));
            table.addCell(new Paragraph("测试名字1", fontZH));
            table.addCell(new Paragraph("20", fontZH));
            table.addCell(new Paragraph("2", fontZH));
            table.addCell(new Paragraph("女", fontZH));
            table.addCell(new Paragraph("测试名字2", fontZH));
            table.addCell(new Paragraph("21", fontZH));*/
/*             table.addCell(new Paragraph("2", fontZH));
             table.addCell(new Paragraph("测试多行的情况出来了", fontZH));*/
//            document.add(section1);
            document.add(section2);
            document.add(new Paragraph("\n"));

            // 第七步，关闭document
            document.close();
            System.out.println("导出pdf成功~");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public static String getCurrentOperatingSystemFontPath(){
        String os = System.getProperty("os.name").toLowerCase();
        // System.out.println("---------当前操作系统是-----------" + os);
        if("linux".equals(os)){
            return "/Users/xxxx/code/simsun/simsun.ttc";
        }else if(os.contains("mac")){
            return "/Users/xxxx/code/simsun/simsun.ttc";
        }
        return "C:/windows/fonts/simsun.ttc,1";
    }
}
