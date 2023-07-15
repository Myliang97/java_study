package com.liang.common.exception;

import com.liang.common.exception.CustomException;
import com.liang.common.result.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result error(Exception e){
        return Result.fail().message(e.getMessage());
    }

    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public Result specificError(ArithmeticException e){
        return Result.fail().message("特定异常处理");
    }

    @ExceptionHandler(CustomException.class)
    @ResponseBody
    public Result customError(CustomException e){
        return Result.fail().code(e.getCode()).message(e.getMsg());
    }
}
