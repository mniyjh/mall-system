package com.mall.mapper;

import com.mall.entity.Goods;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

@Mapper
public interface GoodsMapper {

    @Insert("INSERT INTO goods(goods_name, category_id, price, stock, image, description, status) " +
            "VALUES(#{goodsName}, #{categoryId}, #{price}, #{stock}, #{image}, #{description}, #{status})")
    @Options(useGeneratedKeys = true, keyProperty = "goodsId")
    int insert(Goods goods);

    @Update("UPDATE goods SET goods_name=#{goodsName}, category_id=#{categoryId}, price=#{price}, " +
            "stock=#{stock}, image=#{image}, description=#{description} WHERE goods_id=#{goodsId}")
    int update(Goods goods);

    @Update("UPDATE goods SET status=#{status} WHERE goods_id=#{goodsId}")
    int updateStatus(@Param("goodsId") Long goodsId, @Param("status") Integer status);

    /**
     * 库存扣减（原子操作）：仅当库存充足时才扣减，返回受影响行数
     */
    @Update("UPDATE goods SET stock = stock - #{quantity} WHERE goods_id = #{goodsId} AND stock >= #{quantity}")
    int deductStock(@Param("goodsId") Long goodsId, @Param("quantity") Integer quantity);

    /**
     * 库存恢复（取消订单时回补库存）
     */
    @Update("UPDATE goods SET stock = stock + #{quantity} WHERE goods_id = #{goodsId}")
    int restoreStock(@Param("goodsId") Long goodsId, @Param("quantity") Integer quantity);

    @Select("SELECT * FROM goods WHERE goods_id = #{goodsId}")
    Goods selectById(@Param("goodsId") Long goodsId);

    /**
     * 查询商品列表：status 为空时查询全部，否则按状态过滤
     */
    @Select("<script>SELECT * FROM goods " +
            "<where><if test='status != null'>status = #{status}</if></where> " +
            "ORDER BY goods_id</script>")
    List<Goods> selectList(@Param("status") Integer status);
}
