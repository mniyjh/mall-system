package com.mall.service;

import com.mall.common.BusinessException;
import com.mall.entity.Order;
import com.mall.entity.Shipping;
import com.mall.mapper.OrderMapper;
import com.mall.mapper.ShippingMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

/**
 * 物流发货服务：发货单生成、物流信息录入与更新（发货流程）
 */
@Service
@RequiredArgsConstructor
public class ShippingService {

    private final ShippingMapper shippingMapper;
    private final OrderMapper orderMapper;

    /**
     * 发货：生成发货单并登记物流信息，订单由待发货流转为已发货
     */
    @Transactional
    public Shipping ship(Long orderId, String trackingNo, String company) {
        if (trackingNo == null || trackingNo.isBlank()) {
            throw new BusinessException("物流单号不能为空");
        }
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (orderMapper.updateStatus(orderId, Order.STATUS_TO_SHIP, Order.STATUS_SHIPPED) == 0) {
            throw new BusinessException("订单状态不允许发货（需为待发货）");
        }
        orderMapper.updateShipTime(orderId, LocalDateTime.now());

        Shipping shipping = new Shipping();
        shipping.setOrderId(orderId);
        shipping.setTrackingNo(trackingNo);
        shipping.setCompany(company);
        shipping.setShipTime(LocalDateTime.now());
        shippingMapper.insert(shipping);
        return shipping;
    }

    public Shipping getByOrderId(Long orderId) {
        Shipping shipping = shippingMapper.selectByOrderId(orderId);
        if (shipping == null) {
            throw new BusinessException("该订单暂无物流信息");
        }
        return shipping;
    }

    /**
     * 更新物流信息（物流单号、物流公司）
     */
    public Shipping update(Long shippingId, String trackingNo, String company) {
        Shipping shipping = shippingMapper.selectById(shippingId);
        if (shipping == null) {
            throw new BusinessException("物流记录不存在");
        }
        if (trackingNo != null && !trackingNo.isBlank()) {
            shipping.setTrackingNo(trackingNo);
        }
        if (company != null && !company.isBlank()) {
            shipping.setCompany(company);
        }
        shippingMapper.update(shipping);
        return shipping;
    }
}
