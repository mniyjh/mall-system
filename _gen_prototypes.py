# -*- coding: utf-8 -*-
import os
from playwright.sync_api import sync_playwright

BASE = r"D:\20260907实训\商城系统\goods"
OUT_DIR = os.path.join(BASE, "diagrams", "design")
CHROME = r"C:\Users\yjh11\AppData\Local\ms-playwright\chromium-1223\chrome-win64\chrome.exe"

CSS = """
<meta charset="utf-8">
<style>
*{margin:0;padding:0;box-sizing:border-box}
body{font-family:"Microsoft YaHei","PingFang SC",sans-serif;background:#f5f7fa;color:#333}
a{text-decoration:none;color:#333}
.btn{display:inline-block;padding:8px 18px;border-radius:4px;font-size:14px;cursor:pointer;border:none}
.btn-primary{background:#2c7be5;color:#fff}
.btn-danger{background:#e64a3b;color:#fff}
.btn-sm{padding:4px 10px;font-size:12px}
table{width:100%;border-collapse:collapse;background:#fff}
th,td{border:1px solid #e8ecf1;padding:10px 12px;font-size:13px;text-align:left}
th{background:#f5f7fa;font-weight:600;color:#555}
.tag{display:inline-block;padding:2px 8px;border-radius:3px;font-size:12px}
.tag-green{background:#e6f7e6;color:#18a058}
.tag-blue{background:#e8f1fd;color:#2c7be5}
.tag-gray{background:#f0f0f0;color:#999}
.tag-orange{background:#fff3e0;color:#f57c00}
</style>
"""

# ---------------- 1. 登录页 ----------------
login = CSS + """
<body style="display:flex;align-items:center;justify-content:center;height:100vh;background:linear-gradient(135deg,#2c7be5,#6aa7f0)">
  <div style="width:380px;background:#fff;border-radius:8px;padding:40px;box-shadow:0 8px 30px rgba(0,0,0,.15)">
    <div style="text-align:center;margin-bottom:28px">
      <div style="font-size:22px;font-weight:700;color:#2c7be5">商城系统</div>
      <div style="font-size:13px;color:#999;margin-top:6px">欢迎登录</div>
    </div>
    <div style="margin-bottom:16px">
      <label style="font-size:13px;color:#666;display:block;margin-bottom:6px">用户名</label>
      <input placeholder="请输入用户名" style="width:100%;padding:10px;border:1px solid #dcdfe6;border-radius:4px;font-size:14px">
    </div>
    <div style="margin-bottom:16px">
      <label style="font-size:13px;color:#666;display:block;margin-bottom:6px">密码</label>
      <input type="password" placeholder="请输入密码" style="width:100%;padding:10px;border:1px solid #dcdfe6;border-radius:4px;font-size:14px">
    </div>
    <div style="margin-bottom:20px;font-size:12px;color:#999">
      <input type="checkbox" checked> 记住我
    </div>
    <button class="btn btn-primary" style="width:100%;padding:11px;font-size:15px">登 录</button>
    <div style="text-align:center;font-size:12px;color:#999;margin-top:16px">还没有账号？<span style="color:#2c7be5">立即注册</span></div>
  </div>
</body>
"""

# ---------------- 2. 首页（买家端） ----------------
home = CSS + """
<body>
  <div style="background:#fff;border-bottom:1px solid #e8ecf1">
    <div style="max-width:1000px;margin:0 auto;display:flex;align-items:center;justify-content:space-between;padding:14px 20px">
      <div style="font-size:20px;font-weight:700;color:#2c7be5">商城系统</div>
      <div style="flex:1;max-width:360px;margin:0 30px">
        <div style="display:flex;border:2px solid #2c7be5;border-radius:4px;overflow:hidden">
          <input placeholder="搜索商品" style="flex:1;padding:8px 12px;border:none;font-size:14px">
          <button class="btn btn-primary" style="border-radius:0">搜索</button>
        </div>
      </div>
      <div style="font-size:13px;color:#666">购物车 &nbsp;|&nbsp; 我的订单 &nbsp;|&nbsp; 登录/注册</div>
    </div>
  </div>
  <div style="max-width:1000px;margin:0 auto;padding:20px">
    <div style="background:linear-gradient(90deg,#2c7be5,#6aa7f0);color:#fff;border-radius:8px;padding:40px;margin-bottom:20px">
      <div style="font-size:24px;font-weight:700">全场好物 · 特惠热卖</div>
      <div style="font-size:14px;opacity:.9;margin-top:8px">新用户专享优惠，全场包邮</div>
    </div>
    <div style="font-size:16px;font-weight:600;margin-bottom:14px">热门商品</div>
    <div style="display:grid;grid-template-columns:repeat(4,1fr);gap:16px">
      {cards}
    </div>
  </div>
</body>
"""
_cards = ""
for i in range(1, 5):
    names = ["无线蓝牙耳机", "智能保温杯", "纯棉T恤", "便携充电宝"]
    prices = ["199.00", "89.00", "59.00", "129.00"]
    colors = ["#e8f1fd", "#fff3e0", "#e6f7e6", "#fdecea"]
    _cards += f'''
    <div style="background:#fff;border-radius:8px;overflow:hidden;box-shadow:0 2px 8px rgba(0,0,0,.06)">
      <div style="height:120px;background:{colors[i-1]};display:flex;align-items:center;justify-content:center;font-size:14px;color:#999">商品图片</div>
      <div style="padding:12px">
        <div style="font-size:14px;margin-bottom:8px">{names[i-1]}</div>
        <div style="color:#e64a3b;font-size:16px;font-weight:700">¥{prices[i-1]}</div>
        <div style="margin-top:8px"><button class="btn btn-primary btn-sm">加入购物车</button></div>
      </div>
    </div>'''
home = home.replace("{cards}", _cards)

# ---------------- 3. 购物车页 ----------------
cart = CSS + """
<body>
  <div style="background:#fff;border-bottom:1px solid #e8ecf1;padding:14px 20px">
    <div style="max-width:1000px;margin:0 auto;font-size:18px;font-weight:700">我的购物车</div>
  </div>
  <div style="max-width:1000px;margin:20px auto">
    <table>
      <tr><th style="width:40px"><input type="checkbox" checked></th><th>商品</th><th style="width:100px">单价</th><th style="width:120px">数量</th><th style="width:100px">小计</th><th style="width:80px">操作</th></tr>
      <tr><td><input type="checkbox" checked></td><td>无线蓝牙耳机</td><td>¥199.00</td><td>1</td><td>¥199.00</td><td><span style="color:#e64a3b">删除</span></td></tr>
      <tr><td><input type="checkbox" checked></td><td>智能保温杯</td><td>¥89.00</td><td>2</td><td>¥178.00</td><td><span style="color:#e64a3b">删除</span></td></tr>
      <tr><td><input type="checkbox"></td><td>纯棉T恤</td><td>¥59.00</td><td>1</td><td>¥59.00</td><td><span style="color:#e64a3b">删除</span></td></tr>
    </table>
    <div style="background:#fff;margin-top:16px;padding:16px;border-radius:8px;display:flex;align-items:center;justify-content:flex-end;gap:20px">
      <span style="font-size:13px;color:#666">已选 3 件商品</span>
      <span style="font-size:13px">合计：<span style="color:#e64a3b;font-size:20px;font-weight:700">¥436.00</span></span>
      <button class="btn btn-primary" style="padding:10px 30px">去结算</button>
    </div>
  </div>
</body>
"""

# ---------------- 4. 后台商品管理 ----------------
admin_css = CSS + """
.layout{display:flex;height:100vh}
.sidebar{width:200px;background:#263238;color:#fff;padding-top:10px;flex-shrink:0}
.sidebar .logo{font-size:16px;font-weight:700;padding:14px 20px;border-bottom:1px solid #37474f}
.sidebar .item{padding:13px 20px;font-size:14px;cursor:pointer}
.sidebar .item.active{background:#2c7be5}
.main{flex:1;display:flex;flex-direction:column}
.topbar{background:#fff;padding:14px 20px;border-bottom:1px solid #e8ecf1;display:flex;justify-content:space-between;align-items:center}
.content{padding:20px}
.panel{background:#fff;border-radius:8px;padding:16px}
"""
product_admin = admin_css + """
<body>
  <div class="layout">
    <div class="sidebar">
      <div class="logo">商城系统后台</div>
      <div class="item active">商品管理</div>
      <div class="item">订单管理</div>
      <div class="item">发货管理</div>
      <div class="item">售后管理</div>
      <div class="item">用户管理</div>
      <div class="item">角色管理</div>
    </div>
    <div class="main">
      <div class="topbar"><span style="font-size:16px;font-weight:600">商品管理</span><span style="font-size:13px;color:#666">运营人员，您好</span></div>
      <div class="content">
        <div class="panel">
          <div style="display:flex;justify-content:space-between;margin-bottom:14px">
            <div><button class="btn btn-primary">+ 添加商品</button></div>
            <div><input placeholder="商品名称" style="padding:6px 10px;border:1px solid #dcdfe6;border-radius:4px"><button class="btn btn-primary btn-sm" style="margin-left:8px">查询</button></div>
          </div>
          <table>
            <tr><th>商品ID</th><th>商品名称</th><th>分类</th><th>价格</th><th>库存</th><th>状态</th><th>操作</th></tr>
            <tr><td>1</td><td>无线蓝牙耳机</td><td>数码</td><td>¥199.00</td><td>100</td><td><span class="tag tag-green">已上架</span></td><td><span class="btn btn-sm" style="color:#2c7be5">修改</span> <span style="color:#999">下架</span></td></tr>
            <tr><td>2</td><td>智能保温杯</td><td>家居</td><td>¥89.00</td><td>200</td><td><span class="tag tag-green">已上架</span></td><td><span class="btn btn-sm" style="color:#2c7be5">修改</span> <span style="color:#999">下架</span></td></tr>
            <tr><td>3</td><td>纯棉T恤</td><td>服饰</td><td>¥59.00</td><td>50</td><td><span class="tag tag-gray">已下架</span></td><td><span class="btn btn-sm" style="color:#2c7be5">修改</span> <span style="color:#2c7be5">上架</span></td></tr>
            <tr><td>4</td><td>便携充电宝</td><td>数码</td><td>¥129.00</td><td>150</td><td><span class="tag tag-green">已上架</span></td><td><span class="btn btn-sm" style="color:#2c7be5">修改</span> <span style="color:#999">下架</span></td></tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</body>
"""

# ---------------- 5. 后台订单管理 ----------------
order_admin = admin_css + """
<body>
  <div class="layout">
    <div class="sidebar">
      <div class="logo">商城系统后台</div>
      <div class="item">商品管理</div>
      <div class="item active">订单管理</div>
      <div class="item">发货管理</div>
      <div class="item">售后管理</div>
      <div class="item">用户管理</div>
      <div class="item">角色管理</div>
    </div>
    <div class="main">
      <div class="topbar"><span style="font-size:16px;font-weight:600">订单管理</span><span style="font-size:13px;color:#666">运营人员，您好</span></div>
      <div class="content">
        <div class="panel">
          <table>
            <tr><th>订单号</th><th>买家</th><th>金额</th><th>状态</th><th>下单时间</th><th>操作</th></tr>
            <tr><td>202609070001</td><td>张三</td><td>¥436.00</td><td><span class="tag tag-orange">待发货</span></td><td>2026-09-07 10:20</td><td><span class="btn btn-sm" style="color:#2c7be5">发货</span> <span style="color:#999">详情</span></td></tr>
            <tr><td>202609070002</td><td>李四</td><td>¥199.00</td><td><span class="tag tag-blue">已发货</span></td><td>2026-09-07 09:15</td><td><span style="color:#999">详情</span></td></tr>
            <tr><td>202609060010</td><td>王五</td><td>¥89.00</td><td><span class="tag tag-green">已完成</span></td><td>2026-09-06 18:40</td><td><span style="color:#999">详情</span></td></tr>
            <tr><td>202609060005</td><td>赵六</td><td>¥59.00</td><td><span class="tag tag-gray">已取消</span></td><td>2026-09-06 14:30</td><td><span style="color:#999">详情</span></td></tr>
          </table>
        </div>
      </div>
    </div>
  </div>
</body>
"""

PAGES = [
    ("proto_login", login, 800, 600),
    ("proto_home", home, 1000, 760),
    ("proto_cart", cart, 1000, 600),
    ("proto_product_admin", product_admin, 1200, 760),
    ("proto_order_admin", order_admin, 1200, 760),
]

with sync_playwright() as p:
    browser = p.chromium.launch(executable_path=CHROME, headless=True)
    for name, html, w, h in PAGES:
        page = browser.new_page(viewport={"width": w, "height": h})
        page.set_content(html)
        page.wait_for_timeout(300)
        out = os.path.join(OUT_DIR, name + ".png")
        page.screenshot(path=out, full_page=False)
        page.close()
        print("生成:", out)
    browser.close()
print("全部完成")
