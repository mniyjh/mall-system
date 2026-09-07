package com.mall.service;

import com.mall.common.BusinessException;
import com.mall.entity.Goods;
import com.mall.mapper.GoodsMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品管理服务：商品录入、上下架、库存与价格维护（商品上架流程）
 */
@Service
@RequiredArgsConstructor
public class GoodsService {

    private final GoodsMapper goodsMapper;

    /**
     * 录入商品，默认下架状态
     */
    public Goods createGoods(Goods goods) {
        if (goods.getGoodsName() == null || goods.getGoodsName().isBlank()) {
            throw new BusinessException("商品名称不能为空");
        }
        if (goods.getPrice() == null || goods.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new BusinessException("商品价格必须大于0");
        }
        if (goods.getStock() == null || goods.getStock() < 0) {
            throw new BusinessException("库存不能为负数");
        }
        goods.setStatus(goods.getStatus() == null ? 0 : goods.getStatus());
        goodsMapper.insert(goods);
        return goods;
    }

    /**
     * 修改商品信息（名称、分类、价格、库存、描述）
     */
    public Goods updateGoods(Long goodsId, Goods goods) {
        Goods exist = getById(goodsId);
        if (goods.getGoodsName() != null) {
            exist.setGoodsName(goods.getGoodsName());
        }
        if (goods.getCategoryId() != null) {
            exist.setCategoryId(goods.getCategoryId());
        }
        if (goods.getPrice() != null) {
            if (goods.getPrice().compareTo(java.math.BigDecimal.ZERO) <= 0) {
                throw new BusinessException("商品价格必须大于0");
            }
            exist.setPrice(goods.getPrice());
        }
        if (goods.getStock() != null) {
            if (goods.getStock() < 0) {
                throw new BusinessException("库存不能为负数");
            }
            exist.setStock(goods.getStock());
        }
        if (goods.getImage() != null) {
            exist.setImage(goods.getImage());
        }
        if (goods.getDescription() != null) {
            exist.setDescription(goods.getDescription());
        }
        goodsMapper.update(exist);
        return exist;
    }

    /**
     * 上架商品
     */
    public void onShelf(Long goodsId) {
        getById(goodsId);
        goodsMapper.updateStatus(goodsId, 1);
    }

    /**
     * 下架商品
     */
    public void offShelf(Long goodsId) {
        getById(goodsId);
        goodsMapper.updateStatus(goodsId, 0);
    }

    /**
     * 查询商品列表（status 为空查全部，1 查已上架）
     */
    public List<Goods> list(Integer status) {
        return goodsMapper.selectList(status);
    }

    public Goods getById(Long goodsId) {
        Goods goods = goodsMapper.selectById(goodsId);
        if (goods == null) {
            throw new BusinessException("商品不存在：" + goodsId);
        }
        return goods;
    }
}
