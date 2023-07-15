package com.liang.common.exception;

import com.liang.common.result.ResultCodeEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomException extends RuntimeException {

    public CustomException(ResultCodeEnum code){
        this.code = code.getCode();
        this.msg = code.getMessage();
    }
    private Integer code;
    private String msg;
}
