<template>
  <div class="panel">
    <div class="section-title">
      <div>
        <h3>库存管理</h3>
        <p>出入库流水、库存余额和预警记录</p>
      </div>
      <el-button type="primary" @click="visible = true">登记流水</el-button>
    </div>
    <el-table :data="rows" height="650">
      <el-table-column prop="productName" label="商品" min-width="190" />
      <el-table-column prop="type" label="类型" width="100">
        <template #default="{ row }">
          <el-tag :type="row.type === '预警' ? 'danger' : row.type === '入库' ? 'success' : 'warning'">
            {{ row.type }}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="quantity" label="数量" width="100" />
      <el-table-column prop="balance" label="余额" width="100" />
      <el-table-column prop="operatorName" label="操作人" width="120" />
      <el-table-column prop="remark" label="备注" />
    </el-table>

    <el-dialog v-model="visible" title="库存流水" width="520">
      <el-form :model="form" label-width="90px">
        <el-form-item label="商品"><el-input v-model="form.productName" /></el-form-item>
        <el-form-item label="类型">
          <el-radio-group v-model="form.type">
            <el-radio-button label="入库" />
            <el-radio-button label="出库" />
            <el-radio-button label="预警" />
          </el-radio-group>
        </el-form-item>
        <el-form-item label="数量"><el-input-number v-model="form.quantity" /></el-form-item>
        <el-form-item label="余额"><el-input-number v-model="form.balance" /></el-form-item>
        <el-form-item label="备注"><el-input v-model="form.remark" /></el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="visible = false">取消</el-button>
        <el-button type="primary" @click="save">保存</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import { onMounted, reactive, ref, watch } from 'vue'
import { inventoryApi } from '../api/modules'

const rows = ref([])
const visible = ref(false)
const form = reactive({
  productName: '', type: '入库', quantity: 0,
  balance: 0, operatorName: '管理员', remark: ''
})

watch(visible, (v) => {
  if (v) Object.assign(form, {
    productName: '', type: '入库', quantity: 0,
    balance: 0, operatorName: '管理员', remark: ''
  })
})

async function load() { rows.value = await inventoryApi.list() }

async function save() {
  await inventoryApi.create(form)
  visible.value = false
  load()
}

onMounted(load)
</script>
