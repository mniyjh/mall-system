package com.mall.controller;

import com.mall.common.Result;
import com.mall.dto.CartRequest;
import com.mall.entity.Cart;
import com.mall.service.CartService;
import com.mall.vo.CartItemVO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 购物车接口（购买流程前置环节）
 */
@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    /** 加入购物车 */
    @PostMapping
    public Result<Cart> add(@RequestBody CartRequest req) {
        return Result.ok(cartService.addToCart(req));
    }

    /** 查询购物车 */
    @GetMapping("/{userId}")
    public Result<List<CartItemVO>> list(@PathVariable Long userId) {
        return Result.ok(cartService.list(userId));
    }

    /** 修改购物车数量 */
    @PutMapping("/{id}")
    public Result<Void> update(@PathVariable Long id, @RequestParam Integer quantity) {
        cartService.updateQuantity(id, quantity);
        return Result.ok();
    }

    /** 删除购物车项 */
    @DeleteMapping("/{id}")
    public Result<Void> remove(@PathVariable Long id) {
        cartService.remove(id);
        return Result.ok();
    }
}
