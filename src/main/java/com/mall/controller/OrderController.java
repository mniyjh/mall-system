package com.mall.controller;

import com.mall.common.Result;
import com.mall.dto.OrderCreateRequest;
import com.mall.entity.Order;
import com.mall.service.OrderService;
import com.mall.vo.OrderDetailVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 订单接口（购买流程）
 */
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    /** 下单（扣减库存，生成订单） */
    @PostMapping
    public Result<Order> create(@RequestBody OrderCreateRequest req) {
        return Result.ok(orderService.createOrder(req));
    }

    /** 支付订单 */
    @PostMapping("/{id}/pay")
    public Result<Void> pay(@PathVariable Long id) {
        orderService.pay(id);
        return Result.ok();
    }

    /** 取消订单（回补库存） */
    @PutMapping("/{id}/cancel")
    public Result<Void> cancel(@PathVariable Long id) {
        orderService.cancel(id);
        return Result.ok();
    }

    /** 确认收货（订单完成） */
    @PutMapping("/{id}/confirm-receipt")
    public Result<Void> confirmReceipt(@PathVariable Long id) {
        orderService.confirmReceipt(id);
        return Result.ok();
    }

    /** 订单详情（含明细） */
    @GetMapping("/{id}")
    public Result<OrderDetailVO> detail(@PathVariable Long id) {
        OrderDetailVO vo = new OrderDetailVO();
        vo.setOrder(orderService.getById(id));
        vo.setItems(orderService.getItems(id));
        return Result.ok(vo);
    }

    /** 用户订单列表 */
    @GetMapping("/user/{userId}")
    public Result<List<Order>> listByUser(@PathVariable Long userId) {
        return Result.ok(orderService.listByUser(userId));
    }
}
