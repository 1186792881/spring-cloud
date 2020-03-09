package com.wangyi.example.client.fallback;

import com.wangyi.common.vo.Result;
import com.wangyi.example.client.ServiceExampleClient;
import com.wangyi.example.entity.SeUser;
import org.springframework.stereotype.Component;

@Component
public class ServiceExampleClientFallback implements ServiceExampleClient {

    private static final String EXCEPTION_MSG = "fallback";

    @Override
    public Result<SeUser> getUserById(Long id) {
        return Result.exception(EXCEPTION_MSG);
    }
}
