package com.mall.controller;

import com.mall.common.Result;
import com.mall.entity.Goods;
import com.mall.service.GoodsService;
import lombok.RequiredArgsConstructor;
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
 * 商品管理接口（商品上架流程）
 */
@RestController
@RequestMapping("/api/goods")
@RequiredArgsConstructor
public class GoodsController {

    private final GoodsService goodsService;

    /** 录入商品 */
    @PostMapping
    public Result<Goods> create(@RequestBody Goods goods) {
        return Result.ok(goodsService.createGoods(goods));
    }

    /** 修改商品（价格、库存、名称等维护） */
    @PutMapping("/{id}")
    public Result<Goods> update(@PathVariable Long id, @RequestBody Goods goods) {
        return Result.ok(goodsService.updateGoods(id, goods));
    }

    /** 上架商品 */
    @PutMapping("/{id}/on-shelf")
    public Result<Void> onShelf(@PathVariable Long id) {
        goodsService.onShelf(id);
        return Result.ok();
    }

    /** 下架商品 */
    @PutMapping("/{id}/off-shelf")
    public Result<Void> offShelf(@PathVariable Long id) {
        goodsService.offShelf(id);
        return Result.ok();
    }

    /** 查询商品列表（status 为空查全部，1 查已上架） */
    @GetMapping
    public Result<List<Goods>> list(@RequestParam(required = false) Integer status) {
        return Result.ok(goodsService.list(status));
    }

    /** 查询商品详情 */
    @GetMapping("/{id}")
    public Result<Goods> get(@PathVariable Long id) {
        return Result.ok(goodsService.getById(id));
    }
}
