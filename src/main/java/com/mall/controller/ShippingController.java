package com.mall.controller;

import com.mall.common.Result;
import com.mall.dto.ShipRequest;
import com.mall.entity.Shipping;
import com.mall.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 物流发货接口（发货流程）
 */
@RestController
@RequestMapping("/api/shipping")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    /** 发货（生成发货单并登记物流信息） */
    @PostMapping
    public Result<Shipping> ship(@RequestBody ShipRequest req) {
        return Result.ok(shippingService.ship(req.getOrderId(), req.getTrackingNo(), req.getCompany()));
    }

    /** 查询订单物流信息 */
    @GetMapping("/order/{orderId}")
    public Result<Shipping> getByOrder(@PathVariable Long orderId) {
        return Result.ok(shippingService.getByOrderId(orderId));
    }

    /** 更新物流信息 */
    @PutMapping("/{id}")
    public Result<Shipping> update(@PathVariable Long id, @RequestBody ShipRequest req) {
        return Result.ok(shippingService.update(id, req.getTrackingNo(), req.getCompany()));
    }
}
