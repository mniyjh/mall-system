package com.mall.common;

/**
 * 业务异常：用于业务规则校验失败（如库存不足、订单状态错误等）
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
