# 商城系统（mall-system）

小商品商城系统后端，基于《需求分析规格说明书》与《系统设计文档》实现，覆盖电商平台商品交易的四大业务流程。

## 技术栈

- Java 21 / Spring Boot 3.2 / Spring MVC（REST API）
- MyBatis（数据访问层，对应设计文档的 XxxMapper）
- MySQL 5.7（数据库）
- Maven 3.9

## 四大业务流程

| 流程 | 功能 | 接口 |
|---|---|---|
| 商品上架流程 | 商品录入、上下架、库存与价格维护 | `/api/goods/*` |
| 购买流程 | 购物车、下单、库存扣减、订单状态流转 | `/api/cart/*`、`/api/order/*` |
| 发货流程 | 发货单生成、物流信息录入与更新 | `/api/shipping/*` |
| 收货评价流程 | 确认收货、订单完成、评价管理 | `/api/order/*`、`/api/review/*` |

订单状态流转：`0待支付 → 1待发货 → 2已发货 → 3已完成`，可取消（`0 → 4已取消`，回补库存）。

## 快速开始

### 1. 初始化数据库

```bash
mysql -uroot -p < src/main/resources/sql/schema.sql
mysql -uroot -p < src/main/resources/sql/data.sql
```

（或手动执行这两份 SQL，会创建数据库 `mall_system` 及 6 张表并插入示例商品。）

### 2. 配置数据库连接

编辑 `src/main/resources/application.yml`，把 `spring.datasource.password` 改成你的 MySQL 密码。

### 3. 启动

```bash
mvn spring-boot:run
```

服务启动于 `http://localhost:8080`。

### 4. 闭环测试

```bash
bash test-flow.sh
```

## 接口一览

### 商品 `/api/goods`
- `POST /api/goods` 录入商品
- `PUT /api/goods/{id}` 修改商品（名称/价格/库存/描述）
- `PUT /api/goods/{id}/on-shelf` 上架
- `PUT /api/goods/{id}/off-shelf` 下架
- `GET /api/goods` 商品列表（`?status=1` 查已上架）
- `GET /api/goods/{id}` 商品详情

### 购物车 `/api/cart`
- `POST /api/cart` 加入购物车
- `GET /api/cart/{userId}` 查询购物车
- `PUT /api/cart/{id}?quantity=n` 修改数量
- `DELETE /api/cart/{id}` 删除

### 订单 `/api/order`
- `POST /api/order` 下单（扣库存）
- `POST /api/order/{id}/pay` 支付
- `PUT /api/order/{id}/cancel` 取消（回补库存）
- `PUT /api/order/{id}/confirm-receipt` 确认收货
- `GET /api/order/{id}` 订单详情
- `GET /api/order/user/{userId}` 用户订单

### 发货 `/api/shipping`
- `POST /api/shipping` 发货
- `GET /api/shipping/order/{orderId}` 查询物流
- `PUT /api/shipping/{id}` 更新物流信息

### 评价 `/api/review`
- `POST /api/review` 提交评价
- `GET /api/review/goods/{goodsId}` 商品评价
- `GET /api/review/order/{orderId}` 订单评价
- `GET /api/review/user/{userId}` 用户评价
