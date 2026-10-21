<template>
  <div class="shop-auth-page">
    <RouterLink to="/shop" class="shop-auth-logo">星河商城</RouterLink>
    <section class="shop-auth-card">
      <h1>注册会员</h1>
      <p>注册后可在普通用户端下单付款，订单会同步进入管理员端。</p>
      <el-form :model="form" label-position="top" @keyup.enter="submit">
        <el-form-item label="账号"><el-input v-model="form.username" size="large" /></el-form-item>
        <el-form-item label="昵称"><el-input v-model="form.nickname" size="large" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" size="large" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" size="large" show-password /></el-form-item>
        <el-button type="danger" size="large" class="full-btn" :loading="loading" @click="submit">注册并登录</el-button>
      </el-form>
      <div class="shop-auth-link">已有账号？<RouterLink to="/shop/login">去登录</RouterLink></div>
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
const form = reactive({ username: '', nickname: '', phone: '', password: '' })
async function submit() {
  loading.value = true
  try {
    const user = await shopApi.register(form)
    localStorage.setItem('shop_user', JSON.stringify(user))
    ElMessage.success('注册成功')
    router.push('/shop')
  } finally { loading.value = false }
}
</script>
