package com.yangche.springcloud.service.impl;

import com.yangche.springcloud.service.IMessageProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Source;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.support.MessageBuilder;

import javax.annotation.Resource;
import java.util.UUID;

//@Service 不需要这个注解
@EnableBinding(Source.class)// 定义消息的推送管道
@Slf4j
public class IMessageProviderImpl implements IMessageProvider {

    @Resource
    private MessageChannel output;//消息发送管道,注意这个变量名必须是output

    @Override
    public String send() {
        String serial = UUID.randomUUID().toString();
        Message<String> build = MessageBuilder.withPayload(serial).build();
        boolean send = output.send(build);
        log.info("*************{}", serial);
        return serial + "\t" + send;
    }
}
