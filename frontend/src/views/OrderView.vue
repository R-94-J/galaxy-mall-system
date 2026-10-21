<template>
  <div class="panel">
    <div class="section-title"><div><h3>订单管理</h3><p>查看用户端付款订单、支付方式、收货信息和履约状态</p></div><el-segmented v-model="status" :options="filters" /></div>
    <el-table :data="filtered" height="650" row-key="id">
      <el-table-column type="expand"><template #default="{ row }"><div class="order-items"><strong>商品明细</strong><span v-for="item in row.items" :key="item.id">{{ item.productName }} × {{ item.quantity }}，¥{{ item.price }}</span></div></template></el-table-column>
      <el-table-column prop="orderNo" label="订单号" min-width="180" />
      <el-table-column prop="customerName" label="客户" width="110" />
      <el-table-column prop="receiverPhone" label="手机号" width="130" />
      <el-table-column prop="amount" label="金额" width="110"><template #default="{ row }">¥{{ row.amount }}</template></el-table-column>
      <el-table-column prop="paymentMethod" label="支付" width="110" />
      <el-table-column prop="status" label="状态" width="110"><template #default="{ row }"><el-tag>{{ row.status }}</el-tag></template></el-table-column>
      <el-table-column label="流转" width="170"><template #default="{ row }"><el-select :model-value="row.status" size="small" @change="(v) => changeStatus(row, v)"><el-option v-for="item in workflow" :key="item" :label="item" :value="item" /></el-select></template></el-table-column>
    </el-table>
  </div>
</template>
<script setup>
import { computed, onMounted, ref } from 'vue'
import { orderApi } from '../api/modules'
const rows = ref([])
const status = ref('全部')
const filters = ['全部', '待付款', '待发货', '已发货', '已完成', '售后中']
const workflow = ['待付款', '待发货', '已发货', '已完成', '售后中']
const filtered = computed(() => status.value === '全部' ? rows.value : rows.value.filter((item) => item.status === status.value))
async function load(){ rows.value = await orderApi.list() }
async function changeStatus(row, value){ await orderApi.status(row.id, value); load() }
onMounted(load)
</script>
