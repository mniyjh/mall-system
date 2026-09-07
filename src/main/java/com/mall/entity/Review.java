package com.mall.entity;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * 评价实体（对应评价表 review）
 */
@Data
public class Review {
    private Long reviewId;
    private Long orderId;
    private Long goodsId;
    private Long userId;
    private Integer rating;
    private String content;
    private LocalDateTime createTime;
}
