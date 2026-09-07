package com.mall.service;

import com.mall.common.BusinessException;
import com.mall.entity.Order;
import com.mall.entity.Review;
import com.mall.mapper.OrderMapper;
import com.mall.mapper.ReviewMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评价服务：收货确认后的商品评价（收货评价流程）
 */
@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewMapper reviewMapper;
    private final OrderMapper orderMapper;

    /**
     * 提交评价：仅已完成订单可评价
     */
    public Review createReview(Review review) {
        if (review.getOrderId() == null || review.getGoodsId() == null || review.getUserId() == null) {
            throw new BusinessException("订单、商品、用户不能为空");
        }
        Order order = orderMapper.selectById(review.getOrderId());
        if (order == null) {
            throw new BusinessException("订单不存在");
        }
        if (order.getStatus() != Order.STATUS_COMPLETED) {
            throw new BusinessException("订单未完成，不能评价");
        }
        if (review.getRating() == null || review.getRating() < 1 || review.getRating() > 5) {
            throw new BusinessException("评分需在1-5之间");
        }
        reviewMapper.insert(review);
        return review;
    }

    public List<Review> listByGoods(Long goodsId) {
        return reviewMapper.selectByGoodsId(goodsId);
    }

    public List<Review> listByOrder(Long orderId) {
        return reviewMapper.selectByOrderId(orderId);
    }

    public List<Review> listByUser(Long userId) {
        return reviewMapper.selectByUserId(userId);
    }
}
