package com.jf.datadict.exception;

import com.jf.datadict.model.JSONResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class MyExceptionHandler {

    @ResponseBody
    @ExceptionHandler(ServiceException.class)
    public JSONResult handleException(Exception e){
        return JSONResult.error500(e.getMessage());
    }
}
