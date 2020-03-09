package com.wangyi.common.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@ApiModel(description = "HTTP 请求结果")
@Data
public class Result<T> {

    public static final Integer SUCCESS = 0;
    public static final Integer FAIL = 1;
    public static final Integer EXCEPTION = 2;

    @ApiModelProperty(value = "响应结果描述")
    private String msg;

    @ApiModelProperty(value = "响应状态 0-成功，1-失败，2-异常")
    private Integer code;

    @ApiModelProperty(value = "响应主体")
    private T data;

    public Result() {
        this.code = SUCCESS;
        this.msg = "成功";
    }

    public Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Result(T data) {
        this.code = SUCCESS;
        this.msg = "成功";
        this.data = data;
    }

    public Result(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static <T> Result<T> success(T data) {
        return new Result<T>(data);
    }

    public static Result success() {
        return new Result();
    }

    public static Result fail(String msg) {
        return new Result(FAIL, msg);
    }

    public static Result exception(String msg) {
        return new Result(EXCEPTION, msg);
    }

}
