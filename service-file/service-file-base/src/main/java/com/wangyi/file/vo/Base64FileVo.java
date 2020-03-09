package com.wangyi.file.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(value = "Base64文件对象")
public class Base64FileVo {

    @ApiModelProperty(value = "文件名称")
    private String name;

    @ApiModelProperty(value = "Base64编码后的文件内容")
    private String content;
}
