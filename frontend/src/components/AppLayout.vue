<template>
  <div class="shell">
    <aside class="sidebar">
      <div class="brand">
        <div class="brand-mark">M</div>
        <div>
          <strong>星河商城</strong>
          <span>Admin Center</span>
        </div>
      </div>
      <nav class="nav">
        <RouterLink v-for="item in menus" :key="item.path" :to="item.path" class="nav-item">
          <component :is="item.icon" :size="18" />
          <span>{{ item.label }}</span>
        </RouterLink>
      </nav>
    </aside>
    <main class="workspace">
      <header class="topbar">
        <div>
          <h1>{{ route.meta.title }}</h1>
          <p>{{ today }} · 店铺运营健康度 96%</p>
        </div>
        <div class="top-actions">
          <el-input v-model="keyword" placeholder="搜索商品、订单、会员" clearable />
          <el-button :icon="Bell" circle />
          <el-dropdown>
            <button class="user-chip">
              <CircleUserRound :size="18" />
              {{ user.profile?.realName || '系统管理员' }}
            </button>
            <template #dropdown>
              <el-dropdown-menu>
                <el-dropdown-item @click="logout">退出登录</el-dropdown-item>
              </el-dropdown-menu>
            </template>
          </el-dropdown>
        </div>
      </header>
      <section class="content">
        <router-view />
      </section>
    </main>
  </div>
</template>

<script setup>
import { computed, ref } from 'vue'
import { useRoute, useRouter } from 'vue-router'
import { useUserStore } from '../stores/user'
import {
  Bell,
  Boxes,
  ChartNoAxesCombined,
  CircleUserRound,
  ClipboardList,
  FolderTree,
  PackageCheck,
  Settings,
  ShieldCheck,
  Tags,
  UsersRound
} from 'lucide-vue-next'

const route = useRoute()
const router = useRouter()
const user = useUserStore()
const keyword = ref('')
const today = computed(() => new Date().toLocaleDateString('zh-CN', { month: 'long', day: 'numeric', weekday: 'long' }))

const menus = [
  { label: '数据大屏', path: '/dashboard', icon: ChartNoAxesCombined },
  { label: '商品管理', path: '/products', icon: PackageCheck },
  { label: '分类管理', path: '/categories', icon: FolderTree },
  { label: '订单管理', path: '/orders', icon: ClipboardList },
  { label: '会员管理', path: '/customers', icon: UsersRound },
  { label: '库存管理', path: '/inventory', icon: Boxes },
  { label: '营销管理', path: '/marketing', icon: Tags },
  { label: '用户角色', path: '/users', icon: ShieldCheck },
  { label: '系统设置', path: '/settings', icon: Settings }
]

function logout() {
  user.logout()
  router.push('/login')
}
</script>
