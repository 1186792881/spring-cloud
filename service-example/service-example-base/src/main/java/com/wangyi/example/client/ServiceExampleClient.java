package com.wangyi.example.client;

import com.wangyi.common.vo.Result;
import com.wangyi.example.entity.SeUser;
import com.wangyi.example.client.fallback.ServiceExampleClientFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "service-example", fallback = ServiceExampleClientFallback.class)
public interface ServiceExampleClient {

    @GetMapping("/seUser/get")
    Result<SeUser> getUserById(@RequestParam Long id);
}
