package com.yangche.springcloud.myhandler;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.yangche.springcloud.entities.CommonResult;

/**
 * 不需要让spring管理
 */
public class CustomerBlockHandler {

    /**
     * 注意：方法必须要用static修饰，请注意参数列表中的参数
     * @param exception
     * @return
     */
    public static CommonResult handlerException(BlockException exception) {
        return new CommonResult(4444, "按客戶自定义,global handlerException----1");
    }

    public static CommonResult handlerException2(BlockException exception) {
        return new CommonResult(4444, "按客戶自定义,global handlerException----2");
    }
}
