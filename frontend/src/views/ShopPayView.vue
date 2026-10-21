<template>
  <div class="mall-page shop-checkout-page">
    <header class="shop-simple-header"><RouterLink to="/shop" class="mall-logo"><span>星</span><strong>星河商城</strong></RouterLink><RouterLink to="/shop/orders">我的订单</RouterLink></header>
    <main class="shop-checkout-wrap">
      <section class="shop-pay-panel">
        <div class="section-title"><div><h3>确认订单并付款</h3><p>支付成功后，管理员端订单管理会出现这笔订单。</p></div></div>
        <div v-if="!cartItems.length" class="mall-cart-empty"><ShoppingBag :size="42" /><strong>暂无待支付商品</strong><p>请先返回商城选择商品。</p><el-button type="danger" @click="$router.push('/shop')">去选购</el-button></div>
        <template v-else>
          <div class="shop-pay-items">
            <article v-for="item in cartItems" :key="item.id"><img :src="item.cover" :alt="item.name" /><div><strong>{{ item.name }}</strong><span>¥{{ money(item.price) }} × {{ item.quantity }}</span></div><b>¥{{ money(item.price * item.quantity) }}</b></article>
          </div>
          <el-form :model="form" label-position="top" class="shop-pay-form">
            <el-form-item label="收货人"><el-input v-model="form.customerName" size="large" /></el-form-item>
            <el-form-item label="手机号"><el-input v-model="form.receiverPhone" size="large" /></el-form-item>
            <el-form-item label="收货地址"><el-input v-model="form.address" size="large" /></el-form-item>
            <el-form-item label="支付方式"><el-radio-group v-model="form.paymentMethod"><el-radio-button label="支付宝" /><el-radio-button label="微信支付" /><el-radio-button label="银行卡" /></el-radio-group></el-form-item>
          </el-form>
          <div class="shop-pay-total"><span>应付金额</span><strong>¥{{ money(total) }}</strong><el-button type="danger" size="large" :loading="loading" @click="pay">立即付款</el-button></div>
        </template>
      </section>
    </main>
  </div>
</template>
<script setup>
import { computed, reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { ShoppingBag } from 'lucide-vue-next'
import { shopApi } from '../api/modules'
const router = useRouter()
const user = JSON.parse(localStorage.getItem('shop_user') || 'null')
const cartItems = ref(JSON.parse(sessionStorage.getItem('shop_cart') || '[]'))
const loading = ref(false)
const form = reactive({ customerName: user?.nickname || '', receiverPhone: user?.phone || '', address: '上海市浦东新区星河路 88 号', paymentMethod: '支付宝' })
const total = computed(() => cartItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0))
async function pay() {
  if (!user) { ElMessage.warning('请先登录'); router.push('/shop/login'); return }
  loading.value = true
  try {
    const order = await shopApi.createOrder({ customerId: user.id, customerName: form.customerName, receiverPhone: form.receiverPhone, address: form.address, paymentMethod: form.paymentMethod, amount: total.value, items: cartItems.value.map((item) => ({ productName: item.name, price: item.price, quantity: item.quantity })) })
    sessionStorage.removeItem('shop_cart')
    ElMessage.success(`付款成功，订单号 ${order.orderNo}`)
    router.push('/shop/orders')
  } finally { loading.value = false }
}
function money(value) { return Number(value || 0).toFixed(2) }
</script>
