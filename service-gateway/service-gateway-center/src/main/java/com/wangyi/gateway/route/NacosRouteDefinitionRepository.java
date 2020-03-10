package com.wangyi.gateway.route;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.fastjson.JSON;
import com.alibaba.nacos.api.config.listener.AbstractListener;
import com.alibaba.nacos.api.exception.NacosException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.event.RefreshRoutesEvent;
import org.springframework.cloud.gateway.route.RouteDefinition;
import org.springframework.cloud.gateway.route.RouteDefinitionRepository;
import org.springframework.context.ApplicationEventPublisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

/**
 * Nacos 路由加载器
 * 网关启动时，从 Nacos 中获取配置路由
 */
@Slf4j
public class NacosRouteDefinitionRepository implements RouteDefinitionRepository {

    private static final String DATA_ID = "service-gateway-routes.json";

    private static final String GROUP_ID = "DEFAULT_GROUP";

    private static List<RouteDefinition> routeDefinitions = new ArrayList<>();

    private ApplicationEventPublisher publisher;

    private NacosConfigProperties nacosConfigProperties;

    public NacosRouteDefinitionRepository(ApplicationEventPublisher publisher, NacosConfigProperties nacosConfigProperties) {
        this.publisher = publisher;
        this.nacosConfigProperties = nacosConfigProperties;
        addListener();
    }

    /**
     * 添加Nacos监听
     */
    private void addListener() {
        try {
            nacosConfigProperties.configServiceInstance().addListener(DATA_ID, GROUP_ID, new AbstractListener() {
                @Override
                public void receiveConfigInfo(String configInfo) {
                    log.info("接收到Nacos配置更新事件, config:{}", configInfo);
                    // 更新本地路由
                    if (StrUtil.isNotBlank(configInfo)) {
                        routeDefinitions = JSON.parseArray(configInfo, RouteDefinition.class);
                    }
                    publisher.publishEvent(new RefreshRoutesEvent(this));
                }
            });
        } catch (NacosException e) {
            log.error("Nacos添加监听器失败", e);
        }
    }

    @Override
    public Flux<RouteDefinition> getRouteDefinitions() {
        try {
            // 本地路由不为空，从本地取值
            if (CollUtil.isNotEmpty(routeDefinitions)) {
                return Flux.fromIterable(routeDefinitions);
            }

            // 本地路由为空，从Nacos取值
            String content = nacosConfigProperties.configServiceInstance().getConfig(DATA_ID, GROUP_ID,5000);
            log.info("获取动态路由配置: {}", content);
            if (StrUtil.isNotBlank(content)) {
                routeDefinitions = JSON.parseArray(content, RouteDefinition.class);
            }
            return Flux.fromIterable(routeDefinitions);
        } catch (NacosException e) {
            log.error("获取动态路由失败", e);
        }
        return Flux.fromIterable(routeDefinitions);
    }

    @Override
    public Mono<Void> save(Mono<RouteDefinition> route) {
        return null;
    }

    @Override
    public Mono<Void> delete(Mono<String> routeId) {
        return null;
    }

    public static List<RouteDefinition> getNativeRouteDefinitions() {
        return routeDefinitions;
    }

}
