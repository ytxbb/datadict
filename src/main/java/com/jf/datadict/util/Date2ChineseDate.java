package com.jf.datadict.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Date2ChineseDate {

    private static final String[] NUMBERS = {"〇", "一", "二", "三", "四", "五", "六", "七", "八", "九"};

    private static final String[] data = {"年", "月", "日"};

    /**
     * 通过 yyyy-MM-dd 得到中文大写格式 yyyy MM dd 日期
     */
    public static synchronized String toChinese(String str) {

        StringBuilder sb = new StringBuilder();
        sb.append(getSplitDateStr(str, 0)).append(
                getSplitDateStr(str, 1)).append(
                getSplitDateStr(str, 2));
        return sb.toString();

    }

    public static String getSplitDateStr(String str, int num) {
        String[] DateStr = str.split("-");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < DateStr[num].length(); i++) {
            sb.append(countnum(DateStr[num].substring(i, i + 1)));
        }
        return sb.append(data[num]).toString();
    }

    public static String countnum(String num) {
        return NUMBERS[Integer.valueOf(num)];
    }

    public static void main(String args[]) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sf.format(new Date());
        System.out.println(toChinese(currentDate));

    }

}