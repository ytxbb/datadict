package com.jf.datadict.test;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import com.jf.datadict.exception.ServiceException;
import com.jf.datadict.model.MySqlVO;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Test {

    private static String driverClassName = "com.mysql.jdbc.Driver";

    public static void main1(String[] args) {
        MySqlVO vo = new MySqlVO();
        vo.setIp("1270.123.3.3");
        vo.setPort("3306");
        vo.setUserName("root");
        vo.setPassword("tantan");
        Boolean aBoolean = validauteMySqlConnection(vo);
        System.out.println("是否连接成功"+aBoolean);
    }

    public static Boolean validauteMySqlConnection(MySqlVO vo){
        try {
            Class.forName(driverClassName);
            String url = "jdbc:mysql://" + vo.getIp() + ":" + vo.getPort() + "/mysql?useSSL=false";
            Connection conn= DriverManager.getConnection(url,vo.getUserName(),vo.getPassword());
            System.out.println(conn);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new ServiceException("数据库尝试连接失败！");
        }
        return true;
    }

    public static void main(String[] args) {
        try {
            // 第一步，实例化一个document对象
            Document document = new Document(PageSize.A4);
            // 第二步，设置要导出的路径
            // FileOutputStream out = new  FileOutputStream("H:/workbook111.pdf");
            FileOutputStream out = new  FileOutputStream("C:\\Users\\xyt\\Desktop\\test.pdf");
            //如果是浏览器通过request请求需要在浏览器中输出则使用下面方式
            //OutputStream out = response.getOutputStream();
            // 第三步,设置字符
            BaseFont bfChinese = BaseFont.createFont("C:/windows/fonts/simsun.ttc,1", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
            //BaseFont bfChinese = BaseFont.createFont("STSong-Light", "UniGB-UCS2-H", false);
            Font fontZH = new Font(bfChinese, 12.0F, 0);
            // 第四步，将pdf文件输出到磁盘
            PdfWriter writer = PdfWriter.getInstance(document, out);
            // 第五步，打开生成的pdf文件
            document.open();
            // 第六步,设置内容
            // 纯文本的设置
            Anchor anchorTarget = new Anchor("First page of the document.");
            anchorTarget.setName("BackToTop");
            Paragraph headParagraph = new Paragraph();
            headParagraph.setSpacingBefore(50);

            headParagraph.add(anchorTarget);
            document.add(headParagraph);
            document.add(new Paragraph("Some more text on the first page with different color and font type.",
                    FontFactory.getFont(FontFactory.COURIER,14,Font.BOLD,
                            new CMYKColor(0,255,0,0))));
            // 章节的设置
            Paragraph title1 = new Paragraph("Chapter 1",
                    FontFactory.getFont(FontFactory.HELVETICA, 18, Font.BOLDITALIC,
                            new CMYKColor(0, 255, 255,17)));
            Chapter chapter1 = new Chapter(title1, 1);
            chapter1.setNumberDepth(0);
            // 小标题的设置
            Paragraph title11 = new Paragraph("This is Section 1 in Chapter 1",
                    FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,
                            new CMYKColor(0, 255, 255,17)));
            Section section1 = chapter1.addSection(title11);
            Paragraph someSectionText = new Paragraph("This text comes as part of section 1 of chapter 1.");
            section1.add(someSectionText);
            someSectionText = new Paragraph("Following is a 4 X 2 table.");
            section1.add(someSectionText);
            document.add(chapter1);

            String title = "导出pdf测试的情况";
            document.add(new Paragraph(new Chunk(title, fontZH).setLocalDestination(title)));
            document.add(new Paragraph("\n"));
            // 创建table,注意这里的2是两列的意思,下面通过table.addCell添加的时候必须添加整行内容的所有列
            PdfPTable table = new PdfPTable(4);
            table.setSpacingBefore(25);
            table.setSpacingAfter(25);
            // table.setWidthPercentage(100.0F);
            PdfPCell c1 = new PdfPCell(new Phrase("Header1"));
            table.addCell(c1);
            PdfPCell c2 = new PdfPCell(new Phrase("Header2"));
            table.addCell(c2);
            PdfPCell c3 = new PdfPCell(new Phrase("Header3"));
            table.addCell(c3);
            PdfPCell c4 = new PdfPCell(new Phrase("Header4"));
            table.addCell(c4);
            table.addCell("1.1");
            table.addCell("1.2");
            table.addCell("1.3");
            table.addCell("1.4");
            section1.add(table);

            // 列表对象
            List l = new List(true, false, 10);
            l.add(new ListItem("First item of list"));
            l.add(new ListItem("Second item of list"));
            section1.add(l);

            Paragraph title2 = new Paragraph("Using Anchor",
                    FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD,
                            new CMYKColor(0, 255, 0, 0)));
            section1.add(title2);
            title2.setSpacingBefore(100);
            Anchor anchor2 = new Anchor("Back To Top");
            anchor2.setReference("#BackToTop");
            section1.add(anchor2);

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
            document.add(section1);
            document.add(new Paragraph("\n"));

            // 第七步，关闭document
            document.close();
            System.out.println("导出pdf成功~");

        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }
}
