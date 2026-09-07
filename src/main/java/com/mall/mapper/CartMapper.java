package com.mall.mapper;

import com.mall.entity.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface CartMapper {

    @Insert("INSERT INTO cart(user_id, goods_id, quantity) VALUES(#{userId}, #{goodsId}, #{quantity})")
    @Options(useGeneratedKeys = true, keyProperty = "cartId")
    int insert(Cart cart);

    @Update("UPDATE cart SET quantity = #{quantity} WHERE cart_id = #{cartId}")
    int updateQuantity(@Param("cartId") Long cartId, @Param("quantity") Integer quantity);

    @Delete("DELETE FROM cart WHERE cart_id = #{cartId}")
    int delete(@Param("cartId") Long cartId);

    @Select("SELECT * FROM cart WHERE cart_id = #{cartId}")
    Cart selectById(@Param("cartId") Long cartId);

    @Select("SELECT * FROM cart WHERE user_id = #{userId} ORDER BY cart_id")
    List<Cart> selectByUserId(@Param("userId") Long userId);

    @Select("SELECT * FROM cart WHERE user_id = #{userId} AND goods_id = #{goodsId}")
    Cart selectByUserAndGoods(@Param("userId") Long userId, @Param("goodsId") Long goodsId);
}
