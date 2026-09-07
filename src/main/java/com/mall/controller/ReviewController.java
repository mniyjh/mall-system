package com.mall.controller;

import com.mall.common.Result;
import com.mall.entity.Review;
import com.mall.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 评价接口（收货评价流程）
 */
@RestController
@RequestMapping("/api/review")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    /** 提交评价（仅已完成订单） */
    @PostMapping
    public Result<Review> create(@RequestBody Review review) {
        return Result.ok(reviewService.createReview(review));
    }

    /** 查询商品评价 */
    @GetMapping("/goods/{goodsId}")
    public Result<List<Review>> listByGoods(@PathVariable Long goodsId) {
        return Result.ok(reviewService.listByGoods(goodsId));
    }

    /** 查询订单评价 */
    @GetMapping("/order/{orderId}")
    public Result<List<Review>> listByOrder(@PathVariable Long orderId) {
        return Result.ok(reviewService.listByOrder(orderId));
    }

    /** 查询用户评价 */
    @GetMapping("/user/{userId}")
    public Result<List<Review>> listByUser(@PathVariable Long userId) {
        return Result.ok(reviewService.listByUser(userId));
    }
}
