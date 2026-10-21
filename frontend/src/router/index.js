import { createRouter, createWebHistory } from 'vue-router'
import { useUserStore } from '../stores/user'
import Layout from '../components/AppLayout.vue'

const routes = [
  { path: '/login', component: () => import('../views/LoginView.vue') },
  { path: '/mall', redirect: '/shop' },
  { path: '/shop', component: () => import('../views/MallView.vue'), meta: { public: true, title: '星河商城' } },
  { path: '/shop/login', component: () => import('../views/ShopLoginView.vue'), meta: { public: true, title: '会员登录' } },
  { path: '/shop/register', component: () => import('../views/ShopRegisterView.vue'), meta: { public: true, title: '会员注册' } },
  { path: '/shop/pay', component: () => import('../views/ShopPayView.vue'), meta: { public: true, title: '订单支付' } },
  { path: '/shop/orders', component: () => import('../views/ShopOrdersView.vue'), meta: { public: true, title: '我的订单' } },
  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [
      { path: 'dashboard', component: () => import('../views/DashboardView.vue'), meta: { title: '数据大屏' } },
      { path: 'products', component: () => import('../views/ProductView.vue'), meta: { title: '商品管理' } },
      { path: 'categories', component: () => import('../views/CategoryView.vue'), meta: { title: '分类管理' } },
      { path: 'orders', component: () => import('../views/OrderView.vue'), meta: { title: '订单管理' } },
      { path: 'customers', component: () => import('../views/CustomerView.vue'), meta: { title: '会员管理' } },
      { path: 'inventory', component: () => import('../views/InventoryView.vue'), meta: { title: '库存管理' } },
      { path: 'marketing', component: () => import('../views/MarketingView.vue'), meta: { title: '营销管理' } },
      { path: 'users', component: () => import('../views/UserView.vue'), meta: { title: '用户角色' } },
      { path: 'settings', component: () => import('../views/SettingsView.vue'), meta: { title: '系统设置' } }
    ]
  }
]

const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to) => {
  const store = useUserStore()
  if (to.meta.public) return true
  if (to.path !== '/login' && !store.token) return '/login'
  if (to.path === '/login' && store.token) return '/dashboard'
  return true
})

export default router

