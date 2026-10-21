<template>
  <div class="shop-auth-page">
    <RouterLink to="/shop" class="shop-auth-logo">星河商城</RouterLink>
    <section class="shop-auth-card">
      <h1>会员登录</h1>
      <p>登录后可以付款、查看订单，和管理员后台相互独立。</p>
      <el-form :model="form" label-position="top" @keyup.enter="submit">
        <el-form-item label="账号或手机号"><el-input v-model="form.account" size="large" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" size="large" show-password /></el-form-item>
        <el-button type="danger" size="large" class="full-btn" :loading="loading" @click="submit">登录</el-button>
      </el-form>
      <div class="shop-auth-link">还没有账号？<RouterLink to="/shop/register">立即注册</RouterLink></div>
    </section>
  </div>
</template>
<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { shopApi } from '../api/modules'
const router = useRouter()
const loading = ref(false)
const form = reactive({ account: '', password: '' })
async function submit() {
  loading.value = true
  try {
    const user = await shopApi.login(form)
    localStorage.setItem('shop_user', JSON.stringify(user))
    ElMessage.success('登录成功')
    router.push(sessionStorage.getItem('shop_cart') ? '/shop/pay' : '/shop')
  } finally { loading.value = false }
}
</script>
