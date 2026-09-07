package com.mall.service;

import com.mall.common.BusinessException;
import com.mall.dto.CartRequest;
import com.mall.entity.Cart;
import com.mall.entity.Goods;
import com.mall.mapper.CartMapper;
import com.mall.mapper.GoodsMapper;
import com.mall.vo.CartItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * 购物车服务（购买流程的前置环节）
 */
@Service
@RequiredArgsConstructor
public class CartService {

    private final CartMapper cartMapper;
    private final GoodsMapper goodsMapper;

    /**
     * 加入购物车：若已存在则累加数量
     */
    public Cart addToCart(CartRequest req) {
        if (req.getUserId() == null || req.getGoodsId() == null) {
            throw new BusinessException("用户和商品不能为空");
        }
        int quantity = req.getQuantity() == null ? 1 : req.getQuantity();
        if (quantity <= 0) {
            throw new BusinessException("数量必须大于0");
        }
        Goods goods = goodsMapper.selectById(req.getGoodsId());
        if (goods == null) {
            throw new BusinessException("商品不存在");
        }
        if (goods.getStatus() != 1) {
            throw new BusinessException("商品未上架，无法加入购物车");
        }
        Cart exist = cartMapper.selectByUserAndGoods(req.getUserId(), req.getGoodsId());
        if (exist != null) {
            exist.setQuantity(exist.getQuantity() + quantity);
            cartMapper.updateQuantity(exist.getCartId(), exist.getQuantity());
            return exist;
        }
        Cart cart = new Cart();
        cart.setUserId(req.getUserId());
        cart.setGoodsId(req.getGoodsId());
        cart.setQuantity(quantity);
        cartMapper.insert(cart);
        return cart;
    }

    public void updateQuantity(Long cartId, Integer quantity) {
        if (quantity == null || quantity <= 0) {
            throw new BusinessException("数量必须大于0");
        }
        if (cartMapper.selectById(cartId) == null) {
            throw new BusinessException("购物车项不存在");
        }
        cartMapper.updateQuantity(cartId, quantity);
    }

    public void remove(Long cartId) {
        if (cartMapper.selectById(cartId) == null) {
            throw new BusinessException("购物车项不存在");
        }
        cartMapper.delete(cartId);
    }

    /**
     * 查询购物车（含商品信息与合计）
     */
    public List<CartItemVO> list(Long userId) {
        List<Cart> carts = cartMapper.selectByUserId(userId);
        List<CartItemVO> result = new ArrayList<>();
        for (Cart c : carts) {
            Goods goods = goodsMapper.selectById(c.getGoodsId());
            if (goods == null) {
                continue;
            }
            CartItemVO vo = new CartItemVO();
            vo.setCartId(c.getCartId());
            vo.setGoodsId(c.getGoodsId());
            vo.setGoodsName(goods.getGoodsName());
            vo.setPrice(goods.getPrice());
            vo.setQuantity(c.getQuantity());
            vo.setSubtotal(goods.getPrice().multiply(BigDecimal.valueOf(c.getQuantity())));
            result.add(vo);
        }
        return result;
    }
}
