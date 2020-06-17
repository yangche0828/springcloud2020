package com.yangche.springcloud.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Date;

/**
 * 开启这个过滤器，只要将它加到spring的管理即可
 */
//@Component
@Slf4j
//@Order(value = 0)//实现接口Ordered或此注解，任选其一
public class GatewayFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        //可以在过滤器链中做，鉴权等一些验证的逻辑
        log.info("***********come in MyLogGateWayFilter:  " + new Date());
        //类似httpServerletRequest，可以拿到所有请求的数据
        ServerHttpRequest request = exchange.getRequest();
        String uname = request.getQueryParams().getFirst("uname");
        //随便一个地址，只要带着uname即可
        if (uname == null) {
            log.info("*******用户名为null，非法用户，o(╥﹏╥)o");
            //返回的响应中设置参数，此处响应了一个406的状态码
            ServerHttpResponse response = exchange.getResponse();
            response.setStatusCode(HttpStatus.NOT_ACCEPTABLE);
            //表示响应处理完成，详情看源码描述
            return response.setComplete();
        }
        //表示将过滤器扔到过滤器链中
        return chain.filter(exchange);

    }

    /**
     * 方法一：实现接口Ordered
     * 这个代表在过滤器链中的执行顺序
     * 数值越小优先级越高
     * 方法二：添加注解@Order
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
