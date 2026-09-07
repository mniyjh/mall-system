package com.mall.service;

import com.mall.common.BusinessException;
import com.mall.dto.OrderCreateRequest;
import com.mall.entity.Goods;
import com.mall.entity.Order;
import com.mall.entity.OrderItem;
import com.mall.mapper.GoodsMapper;
import com.mall.mapper.OrderItemMapper;
import com.mall.mapper.OrderMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * 订单服务：下单、库存扣减、订单状态流转（购买流程）
 */
@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final GoodsMapper goodsMapper;

    /**
     * 下单：校验商品并原子扣减库存，生成订单及明细（事务保证一致性）
     */
    @Transactional
    public Order createOrder(OrderCreateRequest req) {
        if (req.getUserId() == null) {
            throw new BusinessException("用户不能为空");
        }
        if (req.getItems() == null || req.getItems().isEmpty()) {
            throw new BusinessException("订单商品不能为空");
        }
        // 1. 逐项校验商品并扣减库存
        BigDecimal total = BigDecimal.ZERO;
        for (OrderCreateRequest.OrderItemRequest item : req.getItems()) {
            if (item.getGoodsId() == null || item.getQuantity() == null || item.getQuantity() <= 0) {
                throw new BusinessException("商品和数量不合法");
            }
            Goods goods = goodsMapper.selectById(item.getGoodsId());
            if (goods == null) {
                throw new BusinessException("商品不存在：" + item.getGoodsId());
            }
            if (goods.getStatus() != 1) {
                throw new BusinessException("商品未上架：" + goods.getGoodsName());
            }
            int affected = goodsMapper.deductStock(goods.getGoodsId(), item.getQuantity());
            if (affected == 0) {
                throw new BusinessException("库存不足：" + goods.getGoodsName());
            }
            total = total.add(goods.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }
        // 2. 生成订单（待支付）
        Order order = new Order();
        order.setOrderNo(generateOrderNo());
        order.setUserId(req.getUserId());
        order.setReceiverName(req.getReceiverName());
        order.setReceiverPhone(req.getReceiverPhone());
        order.setReceiverAddress(req.getReceiverAddress());
        order.setTotalAmount(total);
        order.setStatus(Order.STATUS_UNPAID);
        orderMapper.insert(order);
        // 3. 生成订单明细
        for (OrderCreateRequest.OrderItemRequest item : req.getItems()) {
            Goods goods = goodsMapper.selectById(item.getGoodsId());
            OrderItem oi = new OrderItem();
            oi.setOrderId(order.getOrderId());
            oi.setGoodsId(item.getGoodsId());
            oi.setGoodsName(goods.getGoodsName());
            oi.setPrice(goods.getPrice());
            oi.setQuantity(item.getQuantity());
            oi.setSubtotal(goods.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
            orderItemMapper.insert(oi);
        }
        return order;
    }

    /**
     * 支付：待支付 -> 待发货
     */
    @Transactional
    public void pay(Long orderId) {
        if (orderMapper.updateStatus(orderId, Order.STATUS_UNPAID, Order.STATUS_TO_SHIP) == 0) {
            throw new BusinessException("订单不存在或状态不允许支付");
        }
        orderMapper.updatePayTime(orderId, LocalDateTime.now());
    }

    /**
     * 取消订单：待支付 -> 已取消，并回补库存
     */
    @Transactional
    public void cancel(Long orderId) {
        if (orderMapper.updateStatus(orderId, Order.STATUS_UNPAID, Order.STATUS_CANCELLED) == 0) {
            throw new BusinessException("订单不存在或状态不允许取消");
        }
        for (OrderItem item : orderItemMapper.selectByOrderId(orderId)) {
            goodsMapper.restoreStock(item.getGoodsId(), item.getQuantity());
        }
    }

    /**
     * 确认收货：已发货 -> 已完成
     */
    @Transactional
    public void confirmReceipt(Long orderId) {
        if (orderMapper.updateStatus(orderId, Order.STATUS_SHIPPED, Order.STATUS_COMPLETED) == 0) {
            throw new BusinessException("订单不存在或状态不允许确认收货（需为已发货）");
        }
    }

    public Order getById(Long orderId) {
        Order order = orderMapper.selectById(orderId);
        if (order == null) {
            throw new BusinessException("订单不存在：" + orderId);
        }
        return order;
    }

    /**
     * 订单详情（含明细）
     */
    public List<OrderItem> getItems(Long orderId) {
        return orderItemMapper.selectByOrderId(orderId);
    }

    public List<Order> listByUser(Long userId) {
        return orderMapper.selectByUserId(userId);
    }

    private String generateOrderNo() {
        return "ORD" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmssSSS"))
                + (long) (Math.random() * 900 + 100);
    }
}
