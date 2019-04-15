package com.jf.datadict.constants;

public class ReturnCode {

    public static final int Code_100        = 100;
    public static final int Code_400        = 400;
    public static final int Code_401        = 401;
    public static final int Code_403        = 403;
    public static final int Code_404        = 404;
    public static final int Code_405        = 405;
    public static final int Code_500        = 500;
    public static final int Code_200        = 200;

    public static final String UNAUTHORIZED         = "未授权，登录异常";
    public static final String UNKNOWNACCOUNT       = "未知的帐户";
    public static final String WRONG_REQUEST_METHOD = "错误的请求方式";
    public static final String TYPE_MISMATCH        = "参数类型不正确";
    public static final String MISS_PARAM           = "参数不全";
    public static final String HANDER_NO_FOUND      = "[404]请求路径不存在";
    public static final String ACCESS_DENIED        = "[403]拒绝访问";
    public static final String TOKEN_INVALID        = "登陆已过期,请重新登陆";
}
