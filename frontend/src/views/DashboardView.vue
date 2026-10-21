<template>
  <div class="dashboard">
    <div class="kpi-grid">
      <div v-for="card in cards" :key="card.label" class="kpi-card">
        <div class="kpi-icon" :class="card.tone">
          <component :is="card.icon" :size="22" />
        </div>
        <span>{{ card.label }}</span>
        <strong>{{ card.value }}</strong>
        <small>{{ card.tip }}</small>
      </div>
    </div>

    <div class="chart-grid">
      <ChartCard title="销售趋势" subtitle="近 18 日订单销售额" :option="salesOption" />
      <ChartCard title="订单状态分布" subtitle="实时履约漏斗" :option="statusOption" />
      <ChartCard title="品类销售排行" subtitle="按商品累计销量统计" :option="categoryOption" />
      <ChartCard title="会员增长" subtitle="上半年新增会员趋势" :option="memberOption" />
    </div>

    <div class="two-columns">
      <div class="panel">
        <div class="section-title">
          <h3>最新订单</h3>
          <p>最近成交与履约状态</p>
        </div>
        <el-table :data="overview.recentOrders" height="310">
          <el-table-column prop="orderNo" label="订单号" min-width="150" />
          <el-table-column prop="customerName" label="客户" width="90" />
          <el-table-column prop="amount" label="金额" width="100">
            <template #default="{ row }">¥{{ row.amount }}</template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="90">
            <template #default="{ row }">
              <el-tag effect="light">{{ row.status }}</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </div>

      <div class="panel">
        <div class="section-title">
          <h3>库存预警</h3>
          <p>优先处理低库存商品</p>
        </div>
        <el-table :data="overview.lowStockProducts" height="310">
          <el-table-column prop="name" label="商品" min-width="170" />
          <el-table-column prop="categoryName" label="分类" width="100" />
          <el-table-column prop="stock" label="库存" width="90">
            <template #default="{ row }">
              <el-tag :type="row.stock < 20 ? 'danger' : 'warning'">{{ row.stock }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column prop="status" label="状态" width="80" />
        </el-table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { computed, onMounted, reactive, ref } from 'vue'
import {
  Banknote,
  Boxes,
  PackageCheck,
  ShoppingCart,
  TriangleAlert,
  UsersRound
} from 'lucide-vue-next'
import ChartCard from '../components/ChartCard.vue'
import { dashboardApi } from '../api/modules'

const overview = reactive({ recentOrders: [], lowStockProducts: [] })
const charts = ref({})

const cards = computed(() => [
  { label: '销售额', value: `¥${overview.totalSales || 0}`, tip: '较昨日 +12.8%', icon: Banknote, tone: 'green' },
  { label: '订单数', value: overview.orderCount || 0, tip: `${overview.pendingOrders || 0} 单待发货`, icon: ShoppingCart, tone: 'blue' },
  { label: '商品数', value: overview.productCount || 0, tip: `${overview.onlineProducts || 0} 个上架`, icon: PackageCheck, tone: 'purple' },
  { label: '会员数', value: overview.customerCount || 0, tip: '活跃会员持续增长', icon: UsersRound, tone: 'orange' },
  { label: '低库存', value: overview.lowStockCount || 0, tip: '需要补货关注', icon: TriangleAlert, tone: 'red' },
  { label: '仓储批次', value: '42', tip: '今日出入库稳定', icon: Boxes, tone: 'cyan' }
])

const salesOption = computed(() => ({
  tooltip: { trigger: 'axis' },
  grid: { top: 28, right: 20, left: 45, bottom: 34 },
  xAxis: {
    type: 'category',
    data: charts.value.salesTrend?.labels || [],
    axisLine: { lineStyle: { color: '#d9e2ec' } }
  },
  yAxis: { type: 'value', splitLine: { lineStyle: { color: '#edf2f7' } } },
  series: [{
    type: 'line',
    smooth: true,
    data: charts.value.salesTrend?.values || [],
    areaStyle: { opacity: 0.16 },
    lineStyle: { width: 3 },
    color: '#0f766e'
  }]
}))

const statusOption = computed(() => ({
  tooltip: { trigger: 'item' },
  series: [{
    type: 'pie',
    radius: ['48%', '72%'],
    data: Object.entries(charts.value.orderStatus || {}).map(([name, value]) => ({ name, value })),
    color: ['#0f766e', '#2563eb', '#f59e0b', '#16a34a', '#ef4444']
  }]
}))

const categoryOption = computed(() => ({
  tooltip: {},
  grid: { top: 20, right: 20, left: 70, bottom: 28 },
  xAxis: { type: 'value', splitLine: { lineStyle: { color: '#edf2f7' } } },
  yAxis: { type: 'category', data: Object.keys(charts.value.categorySales || {}) },
  series: [{
    type: 'bar',
    data: Object.values(charts.value.categorySales || {}),
    color: '#2563eb',
    barWidth: 16
  }]
}))

const memberOption = computed(() => ({
  tooltip: { trigger: 'axis' },
  grid: { top: 24, right: 20, left: 45, bottom: 34 },
  xAxis: { type: 'category', data: charts.value.memberGrowth?.labels || [] },
  yAxis: { type: 'value', splitLine: { lineStyle: { color: '#edf2f7' } } },
  series: [{
    type: 'bar',
    data: charts.value.memberGrowth?.values || [],
    color: '#7c3aed',
    barWidth: 22
  }]
}))

onMounted(async () => {
  Object.assign(overview, await dashboardApi.overview())
  charts.value = await dashboardApi.charts()
})
</script>
