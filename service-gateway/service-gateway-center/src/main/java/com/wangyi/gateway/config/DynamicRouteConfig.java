package com.wangyi.gateway.config;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.wangyi.gateway.route.NacosRouteDefinitionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 动态路由配置
 */
@Configuration
@ConditionalOnProperty(prefix = "gateway.dynamic.route", name = "enabled", havingValue = "true")
public class DynamicRouteConfig {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Autowired
    private NacosConfigProperties nacosConfigProperties;

    @Bean
    public NacosRouteDefinitionRepository nacosRouteDefinitionRepository() {
        return new NacosRouteDefinitionRepository(publisher, nacosConfigProperties);
    }
}
