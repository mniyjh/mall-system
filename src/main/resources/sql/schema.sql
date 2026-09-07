-- 商城系统数据库表结构（与《系统设计文档》表4-5~4-11一致）
-- 数据库：mall_system

CREATE DATABASE IF NOT EXISTS mall_system DEFAULT CHARSET utf8mb4;
USE mall_system;

-- 商品表
CREATE TABLE IF NOT EXISTS goods (
    goods_id     BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '商品ID',
    goods_name   VARCHAR(100)  NOT NULL COMMENT '商品名称',
    category_id  BIGINT        DEFAULT NULL COMMENT '分类ID',
    price        DECIMAL(10,2) NOT NULL COMMENT '价格',
    stock        INT           NOT NULL DEFAULT 0 COMMENT '库存',
    image        VARCHAR(200)  DEFAULT NULL COMMENT '图片',
    description  TEXT          DEFAULT NULL COMMENT '描述',
    status       TINYINT       NOT NULL DEFAULT 0 COMMENT '状态(1上架/0下架)',
    create_time  DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='商品表';

-- 购物车表
CREATE TABLE IF NOT EXISTS cart (
    cart_id     BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '购物车ID',
    user_id     BIGINT NOT NULL COMMENT '用户ID',
    goods_id    BIGINT NOT NULL COMMENT '商品ID',
    quantity    INT    NOT NULL DEFAULT 1 COMMENT '数量',
    create_time DATETIME DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='购物车表';

-- 订单表
CREATE TABLE IF NOT EXISTS orders (
    order_id         BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '订单ID',
    order_no         VARCHAR(50)   NOT NULL COMMENT '订单编号',
    user_id          BIGINT        NOT NULL COMMENT '用户ID',
    receiver_name    VARCHAR(50)   DEFAULT NULL COMMENT '收货人',
    receiver_phone   VARCHAR(20)   DEFAULT NULL COMMENT '联系电话',
    receiver_address VARCHAR(200)  DEFAULT NULL COMMENT '收货地址',
    total_amount     DECIMAL(10,2) NOT NULL COMMENT '订单金额',
    status           TINYINT       NOT NULL DEFAULT 0 COMMENT '状态(0待支付/1待发货/2已发货/3已完成/4已取消)',
    create_time      DATETIME      DEFAULT CURRENT_TIMESTAMP COMMENT '下单时间',
    pay_time         DATETIME      DEFAULT NULL COMMENT '支付时间',
    ship_time        DATETIME      DEFAULT NULL COMMENT '发货时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单表';

-- 订单明细表
CREATE TABLE IF NOT EXISTS order_item (
    item_id    BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '明细ID',
    order_id   BIGINT        NOT NULL COMMENT '订单ID',
    goods_id   BIGINT        NOT NULL COMMENT '商品ID',
    goods_name VARCHAR(100)  DEFAULT NULL COMMENT '商品名称',
    price      DECIMAL(10,2) DEFAULT NULL COMMENT '单价',
    quantity   INT           DEFAULT NULL COMMENT '数量',
    subtotal   DECIMAL(10,2) DEFAULT NULL COMMENT '小计'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单明细表';

-- 评价表
CREATE TABLE IF NOT EXISTS review (
    review_id   BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '评价ID',
    order_id    BIGINT       DEFAULT NULL COMMENT '订单ID',
    goods_id    BIGINT       DEFAULT NULL COMMENT '商品ID',
    user_id     BIGINT       DEFAULT NULL COMMENT '用户ID',
    rating      TINYINT      DEFAULT NULL COMMENT '评分(1-5)',
    content     VARCHAR(500) DEFAULT NULL COMMENT '评价内容',
    create_time DATETIME     DEFAULT CURRENT_TIMESTAMP COMMENT '评价时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='评价表';

-- 物流表
CREATE TABLE IF NOT EXISTS shipping (
    shipping_id BIGINT AUTO_INCREMENT PRIMARY KEY COMMENT '物流ID',
    order_id    BIGINT       NOT NULL COMMENT '订单ID',
    tracking_no VARCHAR(50)  DEFAULT NULL COMMENT '物流单号',
    company     VARCHAR(50)  DEFAULT NULL COMMENT '物流公司',
    ship_time   DATETIME     DEFAULT NULL COMMENT '发货时间'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='物流表';
