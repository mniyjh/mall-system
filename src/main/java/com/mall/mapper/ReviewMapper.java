package com.mall.mapper;

import com.mall.entity.Review;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ReviewMapper {

    @Insert("INSERT INTO review(order_id, goods_id, user_id, rating, content) " +
            "VALUES(#{orderId}, #{goodsId}, #{userId}, #{rating}, #{content})")
    @Options(useGeneratedKeys = true, keyProperty = "reviewId")
    int insert(Review review);

    @Select("SELECT * FROM review WHERE goods_id = #{goodsId} ORDER BY review_id DESC")
    List<Review> selectByGoodsId(@Param("goodsId") Long goodsId);

    @Select("SELECT * FROM review WHERE order_id = #{orderId} ORDER BY review_id DESC")
    List<Review> selectByOrderId(@Param("orderId") Long orderId);

    @Select("SELECT * FROM review WHERE user_id = #{userId} ORDER BY review_id DESC")
    List<Review> selectByUserId(@Param("userId") Long userId);
}
