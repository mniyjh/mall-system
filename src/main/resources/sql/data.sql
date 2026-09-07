-- 商城系统种子数据
USE mall_system;

-- 商品数据（对应页面原型的示例商品）
INSERT INTO goods (goods_id, goods_name, category_id, price, stock, image, description, status) VALUES
(1, '无线蓝牙耳机', 1, 199.00, 100, NULL, '高音质无线蓝牙耳机', 1),
(2, '智能保温杯',   2, 89.00,  200, NULL, '316不锈钢智能保温杯', 1),
(3, '纯棉T恤',     3, 59.00,  50,  NULL, '纯棉舒适圆领短袖', 1),
(4, '便携充电宝',   1, 129.00, 150, NULL, '10000mAh轻薄便携充电宝', 1);
