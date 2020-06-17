package com.yangche.springcloud.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

@Component
@EnableBinding(Sink.class)
@Slf4j
public class ReceiveMessageListenerController {

    @Value("${server.port}")
    private String serverPort;

    /**
     * 注意，生产者发送的是string，本消费者也要用string
     * 生产者用的方法是withPayload()方法发送，消费者用的是getPayload方法接收
     *
     * @param message
     */
    @StreamListener(Sink.INPUT)
    public void input(Message<String> message) {
        log.info("消费者1号，接收到的消息为：{}\t端口为：{}", message.getPayload(), serverPort);
    }

}
