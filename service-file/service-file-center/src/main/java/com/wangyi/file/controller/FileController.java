package com.wangyi.file.controller;

import cn.hutool.core.codec.Base64Decoder;
import cn.hutool.core.io.FileUtil;
import com.wangyi.common.vo.Result;
import com.wangyi.file.util.MinioFileUtil;
import com.wangyi.file.vo.Base64FileVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

/**
 * @Auther: 汪毅
 * @Date: 2018/6/26 15:35
 * @Description:
 */
@Api(description = "文件接口")
@RestController
@RequestMapping("/file")
@Slf4j
public class FileController {

    @Autowired
    private MinioFileUtil minioFileUtil;

    /**
     * 文件上传
     *
     * @param file 要上传的文件
     * @return
     */
    @ApiOperation(value = "文件上传", notes = "上传一个文件，返回可访问的url")
    @PostMapping("/upload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file", value = "文件", paramType = "form", required = true, dataType = "__file")
    })
    public Result upload(@RequestParam(required = true) MultipartFile file) throws Exception {
        String url = minioFileUtil.upload(file.getInputStream(), FileUtil.extName(file.getOriginalFilename()));
        return Result.success(url);
    }

    /**
     * Base64编码文件上传
     *
     * @param file 要上传的文件
     * @return
     */
    @ApiOperation(value = "文件上传", notes = "上传一个Base64编码文件，返回可访问的url")
    @PostMapping("/uploadBase64")
    public Result upload(@RequestBody(required = true) Base64FileVo file) throws Exception {
        byte[] buff = Base64Decoder.decode(file.getContent());
        String url = minioFileUtil.upload(new ByteArrayInputStream(buff), FileUtil.extName(file.getName()));
        return Result.success(url);
    }

}
