package com.jf.datadict.util;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 通用字符串工具类
 */
public class MyStringUtil {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(final String param) {
        boolean c = false;
        if (param == null || param.trim().isEmpty()) {
            c = true;
        }
        return c;
    }

    /**
     * 判断字符串是否非空
     */
    public static Boolean isNotEmpty(final String param) {
        return !isEmpty(param);
    }

    /**
     * 判断多个字符串全部是否为空
     */
    public static boolean isAllEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isNotEmpty(str)) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断多个字符串其中任意一个是否为空
     */
    public static boolean isHasEmpty(String... strings) {
        if (strings == null) {
            return true;
        }
        for (String str : strings) {
            if (isEmpty(str)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 校验List是否为空
     */
    public static Boolean checkListNotEmpty(List list) {
        Boolean c = false;
        if (list != null && !list.isEmpty() && list.get(0) != null) {
            c = true;
        }
        return c;
    }

    /**
     * 首字母变大写
     */
    public static String firstCharToUpperCase(final String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'a' && firstChar <= 'z') {
            char[] arr = str.toCharArray();
            arr[0] -= ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 首字母变小写
     */
    public static String firstCharToLowerCase(final String str) {
        char firstChar = str.charAt(0);
        if (firstChar >= 'A' && firstChar <= 'Z') {
            char[] arr = str.toCharArray();
            arr[0] += ('a' - 'A');
            return new String(arr);
        }
        return str;
    }

    /**
     * 是否包含汉字
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }

    /**
     * 判断字符串是否全部为数字
     */
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        return pattern.matcher(str).matches();
    }

    /**
     * 使用java正则表达式去掉多余的.与0
     */
    public static String subZeroAndDot(String s) {
        if (s.indexOf(".") > 0) {
            s = s.replaceAll("0+?$", "");//去掉多余的0
            s = s.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return s;
    }

}
