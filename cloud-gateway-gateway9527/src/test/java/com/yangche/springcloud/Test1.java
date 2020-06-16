package com.yangche.springcloud;

import org.junit.Test;

import java.time.ZonedDateTime;
public class Test1 {


    @Test
    public void test1(){
        ZonedDateTime zonedDateTime=ZonedDateTime.now();
        System.out.println(zonedDateTime);
        //时区
        //2020-06-10T21:04:30.710+08:00[Asia/Shanghai]
    }
}
