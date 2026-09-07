#!/bin/bash
# 商城系统四大业务流程闭环测试脚本
# 用法：先启动服务（mvn spring-boot:run），再执行 bash test-flow.sh
BASE="http://localhost:8080"
CT="Content-Type: application/json"

echo "=============================================="
echo "流程一：商品上架流程（商品录入 -> 上架）"
echo "=============================================="
echo "--- 录入商品 ---"
curl -s -X POST "$BASE/api/goods" -H "$CT" \
  -d '{"goodsName":"蓝牙音箱","categoryId":1,"price":299.00,"stock":20,"description":"便携蓝牙音箱","status":0}'
echo
echo "--- 上架商品（新商品 id 假设为 5）---"
curl -s -X PUT "$BASE/api/goods/5/on-shelf"
echo
echo "--- 查询已上架商品 ---"
curl -s "$BASE/api/goods?status=1"
echo

echo "=============================================="
echo "流程二：购买流程（加购物车 -> 下单 -> 库存扣减 -> 支付）"
echo "=============================================="
echo "--- 加入购物车（用户1001，商品1，数量2）---"
curl -s -X POST "$BASE/api/cart" -H "$CT" \
  -d '{"userId":1001,"goodsId":1,"quantity":2}'
echo
echo "--- 查询购物车 ---"
curl -s "$BASE/api/cart/1001"
echo
echo "--- 下单（扣减库存）---"
curl -s -X POST "$BASE/api/order" -H "$CT" \
  -d '{"userId":1001,"receiverName":"张三","receiverPhone":"13800138000","receiverAddress":"重庆市南岸区","items":[{"goodsId":1,"quantity":2}]}'
echo
echo "--- 支付订单（新订单 id 假设为 1）---"
curl -s -X POST "$BASE/api/order/1/pay"
echo
echo "--- 查看商品库存（应从 100 减为 98）---"
curl -s "$BASE/api/goods/1"
echo

echo "=============================================="
echo "流程三：发货流程（发货 -> 物流信息录入）"
echo "=============================================="
echo "--- 发货（生成发货单 + 登记物流）---"
curl -s -X POST "$BASE/api/shipping" -H "$CT" \
  -d '{"orderId":1,"trackingNo":"SF1234567890","company":"顺丰速运"}'
echo
echo "--- 查询物流 ---"
curl -s "$BASE/api/shipping/order/1"
echo

echo "=============================================="
echo "流程四：收货评价流程（确认收货 -> 订单完成 -> 评价）"
echo "=============================================="
echo "--- 确认收货 ---"
curl -s -X PUT "$BASE/api/order/1/confirm-receipt"
echo
echo "--- 提交评价 ---"
curl -s -X POST "$BASE/api/review" -H "$CT" \
  -d '{"orderId":1,"goodsId":1,"userId":1001,"rating":5,"content":"音质很好，物流快"}'
echo
echo "--- 查看订单详情（状态应为 3 已完成）---"
curl -s "$BASE/api/order/1"
echo
echo "--- 查看商品评价 ---"
curl -s "$BASE/api/review/goods/1"
echo

echo "=============================================="
echo "闭环测试完成"
echo "=============================================="
