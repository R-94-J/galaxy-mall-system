# 星河商城管理系统

基于 Spring Boot + Vue 3 + MySQL 的电商管理系统，包含管理后台和商城用户端，内置商品、订单、会员、库存、营销和看板等模块。

## 技术栈

- 后端：Spring Boot 2.7.18、Spring Data JPA、MySQL Connector/J
- 前端：Vue 3、Vite、Pinia、Vue Router、Element Plus、ECharts、Axios
- 数据库：MySQL 8

## 目录结构

```text
galaxy-mall-system
├── backend
│   ├── pom.xml
│   └── src/main
│       ├── java/com/example/malladmin
│       └── resources/application.yml
└── frontend
    ├── package.json
    └── src
```

## 运行环境

- JDK 8+
- Maven 3.6+
- Node.js 20.19+ 或 22.12+
- MySQL 8

## 数据库配置

应用默认连接本机 `mall_admin` 数据库，并在首次启动时自动建表、写入演示数据。

可通过环境变量覆盖数据库账号和密码：

```powershell
$env:DB_USERNAME="root"
$env:DB_PASSWORD="your-password"
```

默认连接地址及账号位于 `backend/src/main/resources/application.yml`。公开仓库中的默认密码仅用于本地演示，请勿直接用于生产环境。

## 启动后端

```bash
cd backend
mvn spring-boot:run
```

后端接口：`http://localhost:8080/api`

## 启动前端

```bash
cd frontend
npm install
npm run dev
```

前端地址：`http://localhost:5173`

## 演示账号

- 管理后台用户名：`admin`
- 管理后台密码：`123456`

商城用户端支持注册新账号。

## 功能模块

- 登录与首页概览
- 数据可视化看板：销售趋势、订单状态、品类销售、库存预警、会员增长
- 商品管理：列表、新增、编辑、删除、上下架、库存展示
- 分类管理：分类层级、排序、状态
- 订单管理：订单列表、状态流转、订单明细
- 会员管理：等级、消费、积分、状态
- 库存管理：库存流水、预警、入库/出库记录
- 营销管理：优惠券、活动指标
- 用户与角色管理
- 商城用户端：商品浏览、购物车、下单、支付页和订单查询
- 店铺配置、运营参数、支付配送配置

## 安全说明

本仓库为教学与演示用途。部署到生产环境前，请修改默认管理员密码、数据库凭据，并启用更严格的身份认证与权限控制。