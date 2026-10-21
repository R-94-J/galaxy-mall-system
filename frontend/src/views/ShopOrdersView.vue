<template>
  <div class="mall-page shop-checkout-page">
    <header class="shop-simple-header"><RouterLink to="/shop" class="mall-logo"><span>星</span><strong>星河商城</strong></RouterLink><RouterLink to="/shop">继续购物</RouterLink></header>
    <main class="shop-checkout-wrap">
      <section class="shop-pay-panel">
        <div class="section-title"><div><h3>我的订单</h3><p>这里显示普通用户端支付成功后的订单。</p></div><el-button @click="load">刷新</el-button></div>
        <div v-if="!user" class="mall-cart-empty"><strong>请先登录会员账号</strong><el-button type="danger" @click="$router.push('/shop/login')">去登录</el-button></div>
        <el-table v-else :data="orders" height="560" row-key="id">
          <el-table-column type="expand"><template #default="{ row }"><div class="order-items"><strong>商品明细</strong><span v-for="item in row.items" :key="item.id">{{ item.productName }} × {{ item.quantity }}，¥{{ item.price }}</span></div></template></el-table-column>
          <el-table-column prop="orderNo" label="订单号" min-width="180" />
          <el-table-column prop="amount" label="金额" width="110"><template #default="{ row }">¥{{ row.amount }}</template></el-table-column>
          <el-table-column prop="paymentMethod" label="支付方式" width="120" />
          <el-table-column prop="status" label="状态" width="110"><template #default="{ row }"><el-tag>{{ row.status }}</el-tag></template></el-table-column>
          <el-table-column prop="createdAt" label="下单时间" min-width="180" />
        </el-table>
      </section>
    </main>
  </div>
</template>
<script setup>
import { onMounted, ref } from 'vue'
import { shopApi } from '../api/modules'
const user = JSON.parse(localStorage.getItem('shop_user') || 'null')
const orders = ref([])
async function load() { if (user?.phone) orders.value = await shopApi.orders(user.phone) }
onMounted(load)
</script>
