package com.mall.mapper;

import com.mall.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
public interface OrderMapper {

    @Insert("INSERT INTO orders(order_no, user_id, receiver_name, receiver_phone, receiver_address, " +
            "total_amount, status) VALUES(#{orderNo}, #{userId}, #{receiverName}, #{receiverPhone}, " +
            "#{receiverAddress}, #{totalAmount}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    int insert(Order order);

    @Select("SELECT * FROM orders WHERE order_id = #{orderId}")
    Order selectById(@Param("orderId") Long orderId);

    @Select("SELECT * FROM orders WHERE order_no = #{orderNo}")
    Order selectByOrderNo(@Param("orderNo") String orderNo);

    @Select("SELECT * FROM orders WHERE user_id = #{userId} ORDER BY order_id DESC")
    List<Order> selectByUserId(@Param("userId") Long userId);

    /**
     * 订单状态流转（乐观校验）：仅当订单处于 fromStatus 时才更新为 toStatus，返回受影响行数
     */
    @Update("UPDATE orders SET status = #{toStatus} WHERE order_id = #{orderId} AND status = #{fromStatus}")
    int updateStatus(@Param("orderId") Long orderId,
                     @Param("fromStatus") Integer fromStatus,
                     @Param("toStatus") Integer toStatus);

    @Update("UPDATE orders SET pay_time = #{payTime} WHERE order_id = #{orderId}")
    int updatePayTime(@Param("orderId") Long orderId, @Param("payTime") LocalDateTime payTime);

    @Update("UPDATE orders SET ship_time = #{shipTime} WHERE order_id = #{orderId}")
    int updateShipTime(@Param("orderId") Long orderId, @Param("shipTime") LocalDateTime shipTime);
}
