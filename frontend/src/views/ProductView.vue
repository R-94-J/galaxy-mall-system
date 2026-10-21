<template>
  <div class="panel">
    <div class="section-title"><div><h3>商品管理</h3><p>维护 SKU、价格、库存和上下架状态</p></div><el-button type="primary" @click="openCreate">新增商品</el-button></div>
    <el-table :data="rows" height="650">
      <el-table-column prop="sku" label="SKU" width="120" />
      <el-table-column prop="name" label="商品名称" min-width="190" />
      <el-table-column prop="categoryName" label="分类" width="120" />
      <el-table-column prop="price" label="售价" width="100"><template #default="{ row }">¥{{ row.price }}</template></el-table-column>
      <el-table-column prop="stock" label="库存" width="90" />
      <el-table-column prop="sales" label="销量" width="90" />
      <el-table-column prop="status" label="状态" width="90"><template #default="{ row }"><el-tag :type="row.status === '上架' ? 'success' : 'info'">{{ row.status }}</el-tag></template></el-table-column>
      <el-table-column label="操作" width="220" fixed="right"><template #default="{ row }"><el-button link type="primary" @click="edit(row)">编辑</el-button><el-button link @click="toggle(row)">{{ row.status === '上架' ? '下架' : '上架' }}</el-button><el-button link type="danger" @click="remove(row)">删除</el-button></template></el-table-column>
    </el-table>
    <el-dialog v-model="visible" :title="form.id ? '编辑商品' : '新增商品'" width="560">
      <el-form :model="form" label-width="90px">
        <el-form-item label="SKU"><el-input v-model="form.sku" /></el-form-item>
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="分类"><el-select v-model="form.categoryName"><el-option v-for="item in categories" :key="item.name" :label="item.name" :value="item.name" /></el-select></el-form-item>
        <el-form-item label="售价"><el-input-number v-model="form.price" :min="0" /></el-form-item>
        <el-form-item label="成本"><el-input-number v-model="form.costPrice" :min="0" /></el-form-item>
        <el-form-item label="库存"><el-input-number v-model="form.stock" :min="0" /></el-form-item>
        <el-form-item label="状态"><el-radio-group v-model="form.status"><el-radio-button label="上架" /><el-radio-button label="下架" /></el-radio-group></el-form-item>
      </el-form>
      <template #footer><el-button @click="visible=false">取消</el-button><el-button type="primary" @click="save">保存</el-button></template>
    </el-dialog>
  </div>
</template>
<script setup>
import { onMounted, reactive, ref } from 'vue'
import { ElMessageBox } from 'element-plus'
import { categoryApi, productApi } from '../api/modules'
const rows = ref([])
const categories = ref([])
const visible = ref(false)
const form = reactive({})
async function load() { rows.value = await productApi.list(); categories.value = await categoryApi.list() }
function openCreate() { Object.assign(form, { id: null, sku: '', name: '', categoryName: '数码家电', price: 0, costPrice: 0, stock: 0, sales: 0, status: '上架' }); visible.value = true }
function edit(row) { Object.assign(form, row); visible.value = true }
async function save() { form.id ? await productApi.update(form.id, form) : await productApi.create(form); visible.value = false; load() }
async function toggle(row) { await productApi.status(row.id, row.status === '上架' ? '下架' : '上架'); load() }
async function remove(row) { await ElMessageBox.confirm(`确认删除 ${row.name}？`, '删除商品'); await productApi.remove(row.id); load() }
onMounted(load)
</script>
