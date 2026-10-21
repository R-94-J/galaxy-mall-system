<template>
  <div class="mall-page">
    <div class="mall-topline">
      <div class="mall-wrap mall-topline-inner">
        <span>欢迎来到星河商城用户端</span>
        <nav>
          <RouterLink to="/shop/orders">我的订单</RouterLink>
          <RouterLink v-if="!shopUser" to="/shop/login">会员登录</RouterLink>
          <RouterLink v-if="!shopUser" to="/shop/register">免费注册</RouterLink>
          <button v-else @click="logout">{{ shopUser.nickname }} · 退出</button>
          <RouterLink to="/login">管理员端</RouterLink>
        </nav>
      </div>
    </div>

    <header class="mall-header">
      <div class="mall-wrap mall-header-inner">
        <RouterLink to="/shop" class="mall-logo" aria-label="星河商城首页">
          <span>星</span>
          <strong>星河商城</strong>
        </RouterLink>
        <div class="mall-search">
          <el-input v-model="keyword" size="large" placeholder="手机、耳机、坚果礼盒" clearable @keyup.enter="commitSearch">
            <template #prefix><Search :size="18" /></template>
          </el-input>
          <el-button type="danger" size="large" @click="commitSearch">搜索</el-button>
          <div class="mall-hotwords">
            <button v-for="word in hotWords" :key="word" @click="quickSearch(word)">{{ word }}</button>
          </div>
        </div>
        <button class="mall-cart-button" @click="cartVisible = true">
          <ShoppingCart :size="19" />
          <span>我的购物车</span>
          <b>{{ cartCount }}</b>
        </button>
      </div>
    </header>

    <main class="mall-main">
      <section class="mall-wrap mall-hero">
        <aside class="mall-category-panel">
          <strong>全部商品分类</strong>
          <button v-for="category in categoryTabs" :key="category" :class="{ active: activeCategory === category }" @click="activeCategory = category">
            <component :is="categoryIcon(category)" :size="18" />
            <span>{{ category }}</span>
            <ChevronRight :size="16" />
          </button>
        </aside>

        <div class="mall-hero-banner">
          <div>
            <h1>品质好物 即刻送达</h1>
            <p>独立普通用户购物端：登录、加购、支付、查订单，和管理员后台分开运行。</p>
            <div class="mall-hero-actions">
              <el-button type="danger" size="large" @click="scrollToProducts">立即选购</el-button>
              <el-button size="large" @click="goMember">{{ shopUser ? '我的订单' : '会员登录' }}</el-button>
            </div>
          </div>
          <div class="mall-hero-card">
            <span>今日爆款</span>
            <strong>{{ featuredProduct.name }}</strong>
            <small>限时价 ¥{{ money(featuredProduct.price) }}</small>
            <button @click="addToCart(featuredProduct)">加入购物车</button>
          </div>
        </div>

        <aside class="mall-user-panel">
          <div class="mall-avatar">{{ shopUser ? shopUser.nickname.slice(0, 1) : '会' }}</div>
          <strong>{{ shopUser ? `Hi，${shopUser.nickname}` : 'Hi，欢迎逛商城' }}</strong>
          <p>{{ shopUser ? '已登录，可直接提交订单并在线支付。' : '登录或注册后即可付款，付款订单会同步到管理员端。' }}</p>
          <div class="mall-user-actions">
            <RouterLink :to="shopUser ? '/shop/orders' : '/shop/login'">{{ shopUser ? '我的订单' : '登录' }}</RouterLink>
            <RouterLink to="/shop/register">注册</RouterLink>
          </div>
          <div class="mall-service-grid">
            <span><Truck :size="18" /> 极速达</span>
            <span><BadgeCheck :size="18" /> 正品保障</span>
            <span><Headphones :size="18" /> 售后无忧</span>
            <span><TicketPercent :size="18" /> 优惠券</span>
          </div>
        </aside>
      </section>

      <section class="mall-wrap mall-channel-row">
        <div class="mall-flash-card">
          <div><span>京选秒杀</span><strong>20:00 场</strong></div>
          <small>距离结束 01:36:28</small>
        </div>
        <article v-for="item in flashProducts" :key="item.id" class="mall-mini-product" @click="addToCart(item)">
          <img :src="item.cover" :alt="item.name" />
          <div><strong>{{ item.name }}</strong><span>¥{{ money(item.price) }}</span></div>
        </article>
      </section>

      <section ref="productSection" class="mall-wrap mall-products-section">
        <div class="mall-section-head">
          <div><h2>为你推荐</h2><p>按分类、关键词和排序快速找到想买的商品</p></div>
          <div class="mall-filter-tools">
            <el-segmented v-model="activeCategory" :options="categoryTabs" />
            <el-select v-model="sortMode" size="large" aria-label="商品排序">
              <el-option label="综合排序" value="default" />
              <el-option label="销量优先" value="sales" />
              <el-option label="价格从低到高" value="priceAsc" />
              <el-option label="价格从高到低" value="priceDesc" />
            </el-select>
          </div>
        </div>

        <div v-if="loading" class="mall-loading"><el-skeleton :rows="4" animated /></div>
        <div v-else class="mall-product-grid">
          <article v-for="item in filteredProducts" :key="item.id" class="mall-product-card">
            <div class="mall-product-cover">
              <img :src="item.cover" :alt="item.name" />
              <button @click="addToCart(item)"><ShoppingCart :size="17" />加购</button>
            </div>
            <div class="mall-product-body">
              <span>{{ item.category }}</span>
              <h3>{{ item.name }}</h3>
              <p>{{ item.subtitle }}</p>
              <div class="mall-product-meta"><strong>¥{{ money(item.price) }}</strong><small>已售 {{ item.sales }}</small></div>
            </div>
          </article>
        </div>
      </section>
    </main>

    <el-drawer v-model="cartVisible" title="我的购物车" size="380px" class="mall-cart-drawer">
      <div v-if="cartItems.length" class="mall-cart-list">
        <article v-for="item in cartItems" :key="item.id" class="mall-cart-item">
          <img :src="item.cover" :alt="item.name" />
          <div><strong>{{ item.name }}</strong><span>¥{{ money(item.price) }}</span><el-input-number v-model="item.quantity" :min="1" :max="99" size="small" /></div>
          <button aria-label="移出购物车" @click="removeFromCart(item.id)"><X :size="16" /></button>
        </article>
      </div>
      <div v-else class="mall-cart-empty"><ShoppingBag :size="42" /><strong>购物车还是空的</strong><p>挑几件喜欢的商品加入购物车吧。</p></div>
      <template #footer>
        <div class="mall-cart-footer"><span>合计 <strong>¥{{ money(cartTotal) }}</strong></span><el-button type="danger" size="large" :disabled="!cartItems.length" @click="checkout">去支付</el-button></div>
      </template>
    </el-drawer>
  </div>
</template>

<script setup>
import { computed, onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import { BadgeCheck, ChevronRight, Headphones, MonitorSmartphone, Search, Shirt, ShoppingBag, ShoppingCart, Sparkles, TicketPercent, Truck, Utensils, X } from 'lucide-vue-next'
import { categoryApi, productApi } from '../api/modules'

const router = useRouter()
const fallbackProducts = [
  { id: 'demo-1', name: 'Aurora X1 智能手表', category: '数码家电', price: 699, sales: 532, stock: 86, cover: 'https://images.unsplash.com/photo-1523275335684-37898b6baf30?auto=format&fit=crop&w=640&q=80' },
  { id: 'demo-2', name: 'ProSound 降噪耳机', category: '数码家电', price: 899, sales: 831, stock: 24, cover: 'https://images.unsplash.com/photo-1505740420928-5e560c06d30e?auto=format&fit=crop&w=640&q=80' },
  { id: 'demo-3', name: '山茶花修护精华', category: '美妆个护', price: 239, sales: 942, stock: 120, cover: 'https://images.unsplash.com/photo-1620916566398-39f1143ab7be?auto=format&fit=crop&w=640&q=80' },
  { id: 'demo-4', name: '氨基酸沐浴套装', category: '美妆个护', price: 129, sales: 611, stock: 18, cover: 'https://images.unsplash.com/photo-1608248597279-f99d160bfcbc?auto=format&fit=crop&w=640&q=80' },
  { id: 'demo-5', name: '都市通勤轻羽外套', category: '服饰鞋包', price: 359, sales: 316, stock: 44, cover: 'https://images.unsplash.com/photo-1496747611176-843222e1e57c?auto=format&fit=crop&w=640&q=80' },
  { id: 'demo-6', name: '云感运动鞋', category: '服饰鞋包', price: 499, sales: 463, stock: 29, cover: 'https://images.unsplash.com/photo-1542291026-7eec264c27ff?auto=format&fit=crop&w=640&q=80' },
  { id: 'demo-7', name: '低温烘焙坚果礼盒', category: '食品生鲜', price: 169, sales: 1221, stock: 220, cover: 'https://images.unsplash.com/photo-1599599810769-bcde5a160d32?auto=format&fit=crop&w=640&q=80' },
  { id: 'demo-8', name: '有机蓝莓鲜果盒', category: '食品生鲜', price: 89, sales: 286, stock: 12, cover: 'https://images.unsplash.com/photo-1498557850523-fd3d118b962e?auto=format&fit=crop&w=640&q=80' }
]
const hotWords = ['降噪耳机', '智能手表', '坚果礼盒', '运动鞋']
const loading = ref(false)
const keyword = ref('')
const committedKeyword = ref('')
const activeCategory = ref('全部')
const sortMode = ref('default')
const categories = ref([])
const products = ref([])
const cartVisible = ref(false)
const cartItems = ref(JSON.parse(sessionStorage.getItem('shop_cart') || '[]'))
const shopUser = ref(JSON.parse(localStorage.getItem('shop_user') || 'null'))
const productSection = ref(null)

const categoryTabs = computed(() => ['全部', ...categories.value])
const featuredProduct = computed(() => products.value[0] || fallbackProducts[0])
const flashProducts = computed(() => filteredProducts.value.slice(0, 4))
const cartCount = computed(() => cartItems.value.reduce((sum, item) => sum + item.quantity, 0))
const cartTotal = computed(() => cartItems.value.reduce((sum, item) => sum + item.price * item.quantity, 0))
const filteredProducts = computed(() => {
  const text = committedKeyword.value.trim().toLowerCase()
  const list = products.value.filter((item) => (activeCategory.value === '全部' || item.category === activeCategory.value) && (!text || `${item.name} ${item.category} ${item.subtitle}`.toLowerCase().includes(text)))
  return [...list].sort((a, b) => sortMode.value === 'sales' ? b.sales - a.sales : sortMode.value === 'priceAsc' ? a.price - b.price : sortMode.value === 'priceDesc' ? b.price - a.price : 0)
})

function looksBroken(text) { return !text || /[�]|涓|灦|鍟|鏁|鎶|闉|洅|帗|妆/.test(text) }
function normalizeProduct(item, index) {
  const fallback = fallbackProducts[index % fallbackProducts.length]
  const hidden = `${item.status || ''}`.includes('下架') || `${item.status || ''}`.includes('涓嬫灦')
  return { id: item.id || fallback.id, name: looksBroken(item.name) ? fallback.name : item.name, category: looksBroken(item.categoryName) ? fallback.category : item.categoryName, price: Number(item.price ?? fallback.price), sales: Number(item.sales ?? fallback.sales), stock: Number(item.stock ?? fallback.stock), cover: item.coverUrl || fallback.cover, subtitle: hidden ? '暂不售卖' : '京选好物 · 支持快速配送' }
}
async function loadMallData() {
  loading.value = true
  try {
    const [productRows, categoryRows] = await Promise.all([productApi.list(), categoryApi.list()])
    products.value = productRows.map(normalizeProduct).filter((item) => item.stock > 0 && item.subtitle !== '暂不售卖')
    if (!products.value.length) products.value = fallbackProducts.map((item) => ({ ...item, subtitle: '京选好物 · 支持快速配送' }))
    const apiCategories = categoryRows.map((item, index) => looksBroken(item.name) ? fallbackProducts[index]?.category : item.name).filter(Boolean)
    categories.value = [...new Set([...apiCategories, ...products.value.map((item) => item.category)])]
  } catch {
    products.value = fallbackProducts.map((item) => ({ ...item, subtitle: '京选好物 · 支持快速配送' }))
    categories.value = [...new Set(fallbackProducts.map((item) => item.category))]
  } finally { loading.value = false }
}
function categoryIcon(category) { return category.includes('数码') ? MonitorSmartphone : category.includes('美妆') ? Sparkles : category.includes('服饰') ? Shirt : category.includes('食品') ? Utensils : ShoppingBag }
function commitSearch() { committedKeyword.value = keyword.value }
function quickSearch(word) { keyword.value = word; committedKeyword.value = word }
function scrollToProducts() { productSection.value?.scrollIntoView({ behavior: 'smooth', block: 'start' }) }
function syncCart() { sessionStorage.setItem('shop_cart', JSON.stringify(cartItems.value)) }
function addToCart(product) { const existing = cartItems.value.find((item) => item.id === product.id); existing ? existing.quantity += 1 : cartItems.value.push({ ...product, quantity: 1 }); syncCart(); ElMessage.success('已加入购物车') }
function removeFromCart(id) { cartItems.value = cartItems.value.filter((item) => item.id !== id); syncCart() }
function checkout() { if (!shopUser.value) { ElMessage.warning('请先登录会员账号'); router.push('/shop/login'); return } syncCart(); router.push('/shop/pay') }
function goMember() { router.push(shopUser.value ? '/shop/orders' : '/shop/login') }
function logout() { localStorage.removeItem('shop_user'); shopUser.value = null; ElMessage.success('已退出会员账号') }
function money(value) { return Number(value || 0).toFixed(2) }
onMounted(loadMallData)
</script>
