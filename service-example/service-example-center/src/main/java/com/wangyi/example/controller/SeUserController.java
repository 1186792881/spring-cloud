package com.wangyi.example.controller;

import cn.hutool.core.lang.Snowflake;
import com.alibaba.fastjson.JSON;
import com.wangyi.common.vo.Result;
import com.wangyi.example.entity.SeUser;
import com.wangyi.example.service.SeUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wangyi
 * @since 2020-02-22
 */
@Api(description = "用户操作接口")
@RestController
@RequestMapping("/seUser")
@Slf4j
public class SeUserController {

    @Autowired
    private Snowflake snowflake;

    @Autowired
    private SeUserService seUserService;

    @ApiOperation(value = "获取用户信息", notes = "根据用户id获取用户信息")
    @GetMapping("/get")
    public Result<SeUser> getUserById(@ApiParam(value = "用户ID", required = true) @RequestParam Long id) {
        log.info("getUserById.prams:{}", JSON.toJSONString(id));
        return Result.success(seUserService.getById(id));
    }

    @ApiOperation(value = "新增用户", notes = "根据用户实体创建用户")
    @PostMapping("/add")
    public Result addUser(@RequestBody SeUser user) {
        log.info("addUser.prams:{}", JSON.toJSONString(user));
        user.setId(snowflake.nextId());
        seUserService.save(user);
        return Result.success();
    }

    @ApiOperation(value = "删除用户", notes = "根据用户id删除用户")
    @GetMapping("/delete")
    public Result deleteUser(@ApiParam(value = "用户ID", required = true) @RequestParam Long id) {
        seUserService.removeById(id);
        return Result.success();
    }

    @ApiOperation(value = "更新用户", notes = "根据用户id更新用户")
    @PostMapping("/update")
    public Result updateUser(@RequestBody SeUser user) {
        seUserService.updateById(user);
        return Result.success();
    }

    @ApiOperation(value = "异常测试", notes = "异常测试")
    @GetMapping("/exception")
    public Result exception() {
        throw new RuntimeException("测试异常");
    }

}
