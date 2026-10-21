<template>
  <div class="panel">
    <div class="section-title">
      <div>
        <h3>分类管理</h3>
        <p>维护前台分类、排序和启用状态</p>
      </div>
      <el-button type="primary" @click="create">新增分类</el-button>
    </div>
    <el-table :data="rows" height="650">
      <el-table-column prop="name" label="分类名称" />
      <el-table-column prop="parentId" label="父级 ID" width="100" />
      <el-table-column prop="sortOrder" label="排序" width="100" />
      <el-table-column prop="icon" label="图标" width="140" />
      <el-table-column prop="status" label="状态" width="100">
        <template #default="{ row }">
          <el-tag>{{ row.status }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template #default="{ row }">
          <el-button link type="primary" @click="edit(row)">编辑</el-button>
          <el-button link type="danger" @click="remove(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="visible" title="分类信息" width="480">
      <el-form :model="form" label-width="90px">
        <el-form-item label="名称"><el-input v-model="form.name" /></el-form-item>
        <el-form-item label="父级 ID"><el-input-number v-model="form.parentId" :min="0" /></el-form-item>
        <el-form-item label="排序"><el-input-number v-model="form.sortOrder" :min="0" /></el-form-item>
        <el-form-item label="图标"><el-input v-model="form.icon" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio-button label="启用" />
            <el-radio-button label="停用" />
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
import { categoryApi } from '../api/modules'

const rows = ref([])
const visible = ref(false)
const form = reactive({})

async function load() { rows.value = await categoryApi.list() }

function create() {
  Object.assign(form, { id: null, name: '', parentId: 0, sortOrder: 1, icon: 'Tag', status: '启用' })
  visible.value = true
}

function edit(row) { Object.assign(form, row); visible.value = true }

async function save() {
  form.id ? await categoryApi.update(form.id, form) : await categoryApi.create(form)
  visible.value = false
  load()
}

async function remove(row) { await categoryApi.remove(row.id); load() }

onMounted(load)
</script>
