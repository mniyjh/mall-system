package com.mall.mapper;

import com.mall.entity.Shipping;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface ShippingMapper {

    @Insert("INSERT INTO shipping(order_id, tracking_no, company, ship_time) " +
            "VALUES(#{orderId}, #{trackingNo}, #{company}, #{shipTime})")
    @Options(useGeneratedKeys = true, keyProperty = "shippingId")
    int insert(Shipping shipping);

    @Select("SELECT * FROM shipping WHERE order_id = #{orderId}")
    Shipping selectByOrderId(@Param("orderId") Long orderId);

    @Select("SELECT * FROM shipping WHERE shipping_id = #{shippingId}")
    Shipping selectById(@Param("shippingId") Long shippingId);

    @Update("UPDATE shipping SET tracking_no = #{trackingNo}, company = #{company}, " +
            "ship_time = #{shipTime} WHERE shipping_id = #{shippingId}")
    int update(Shipping shipping);
}
