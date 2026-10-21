<template>
  <div class="login-page">
    <div class="login-visual">
      <div class="orbit one"></div>
      <div class="orbit two"></div>
      <div class="preview-panel">
        <h2>运营中枢</h2>
        <div class="preview-grid"><span v-for="n in 12" :key="n"></span></div>
        <div class="preview-line"></div>
      </div>
    </div>
    <div class="login-card">
      <h1>星河商城管理系统</h1>
      <p>商品、订单、会员、库存和营销数据一站式管理</p>
      <el-form :model="form" label-position="top" @keyup.enter="submit">
        <el-form-item label="用户名"><el-input v-model="form.username" size="large" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" type="password" size="large" show-password /></el-form-item>
        <el-button type="primary" size="large" class="full-btn" :loading="loading" @click="submit">登录系统</el-button>
      </el-form>
      <div class="login-hint">默认账号：admin / 123456</div>
      <RouterLink class="login-hint" to="/shop">进入普通用户端</RouterLink>
    </div>
  </div>
</template>
<script setup>
import { reactive, ref } from 'vue'
import { useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
const router = useRouter()
const store = useUserStore()
const loading = ref(false)
const form = reactive({ username: 'admin', password: '123456' })
async function submit() { loading.value = true; try { await store.login(form); router.push('/dashboard') } finally { loading.value = false } }
</script>
