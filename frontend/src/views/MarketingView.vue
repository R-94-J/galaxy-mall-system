<template>
  <div class="marketing">
    <div class="metric-strip">
      <div>
        <span>活动 GMV</span>
        <strong>¥286,420</strong>
        <small>直播专场 +18.4%</small>
      </div>
      <div>
        <span>优惠券核销</span>
        <strong>3,148</strong>
        <small>核销率 42.6%</small>
      </div>
      <div>
        <span>拉新会员</span>
        <strong>1,028</strong>
        <small>新客成本 ¥11.8</small>
      </div>
      <div>
        <span>复购率</span>
        <strong>36.2%</strong>
        <small>环比 +4.2%</small>
      </div>
    </div>

    <div class="panel">
      <div class="section-title">
        <div>
          <h3>优惠券管理</h3>
          <p>满减券、会员券和活动券投放管理</p>
        </div>
        <el-button type="primary" @click="create">新增优惠券</el-button>
      </div>
      <el-table :data="rows" height="560">
        <el-table-column prop="name" label="券名称" min-width="190" />
        <el-table-column prop="thresholdAmount" label="门槛" width="110">
          <template #default="{ row }">¥{{ row.thresholdAmount }}</template>
        </el-table-column>
        <el-table-column prop="discountAmount" label="优惠" width="110">
          <template #default="{ row }">¥{{ row.discountAmount }}</template>
        </el-table-column>
        <el-table-column prop="issuedCount" label="发放" width="100" />
        <el-table-column prop="usedCount" label="核销" width="100" />
        <el-table-column prop="status" label="状态" width="100">
          <template #default="{ row }">
            <el-tag>{{ row.status }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="有效期" width="210">
          <template #default="{ row }">{{ row.startDate }} 至 {{ row.endDate }}</template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog v-model="visible" title="优惠券" width="520">
      <el-form :model="form" label-width="90px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="门槛"><el-input-number v-model="form.thresholdAmount" /></el-form-item>
        <el-form-item label="优惠"><el-input-number v-model="form.discountAmount" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio-button label="未开始" />
            <el-radio-button label="进行中" />
            <el-radio-button label="已结束" />
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref } from 'vue'
import { couponApi } from '../api/modules'

const rows = ref([])
const visible = ref(false)
const form = reactive({})

async function load() { rows.value = await couponApi.list() }

function create() {
  Object.assign(form, {
    id: null, name: '', thresholdAmount: 0, discountAmount: 0,
    issuedCount: 0, usedCount: 0, status: '未开始',
    startDate: '2026-07-01', endDate: '2026-08-01'
  })
  visible.value = true
}

async function save() {
  form.id ? await couponApi.update(form.id, form) : await couponApi.create(form)
  visible.value = false
  load()
}

onMounted(load)
</script>
