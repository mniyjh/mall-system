package com.mall.vo;

import com.mall.entity.Order;
import com.mall.entity.OrderItem;
import lombok.Data;

import java.util.List;

/**
 * 订单详情视图对象（订单 + 明细）
 */
@Data
public class OrderDetailVO {
    private Order order;
    private List<OrderItem> items;
}
