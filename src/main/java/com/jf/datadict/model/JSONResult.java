package com.jf.datadict.model;

import com.jf.datadict.constants.ReturnCode;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JSONResult {
	
    private Integer status;	// 响应业务状态
    private String msg;		// 响应消息
    private Object data;	// 响应中的数据

    private JSONResult(Integer status, String msg, Object data) {
        this.status = status;
        this.msg = msg;
        this.data = data;
    }

    public static JSONResult ok() {
    	return new JSONResult(ReturnCode.Code_200, "OK", null);
    }

    public static JSONResult ok(Object data) {
        return new JSONResult(ReturnCode.Code_200, "OK", data);
    }

    public static JSONResult error500(String msg) {
        return new JSONResult(ReturnCode.Code_500, msg, null);
    }

    public static JSONResult build(Integer status, String msg, Object data) {
    	return new JSONResult(status, msg, data);
    }

    public static JSONResult build(Integer status, String msg) {
        return new JSONResult(status, msg, null);
    }
}