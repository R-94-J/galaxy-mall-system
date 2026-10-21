<template>
  <div class="panel">
    <div class="section-title">
      <div>
        <h3>会员管理</h3>
        <p>会员账号、等级、积分、消费金额和状态</p>
      </div>
      <el-button type="primary" @click="create">新增会员</el-button>
    </div>
    <el-table :data="rows" height="650">
      <el-table-column prop="username" label="账号" />
      <el-table-column prop="nickname" label="昵称" />
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="levelName" label="等级">
        <template #default="{ row }">
          <el-tag type="success">{{ row.levelName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="points" label="积分" />
      <el-table-column prop="totalSpend" label="累计消费">
        <template #default="{ row }">¥{{ row.totalSpend }}</template>
      </el-table-column>
      <el-table-column prop="status" label="状态" />
      <el-table-column label="操作">
        <template #default="{ row }">
          <el-button link type="primary" @click="edit(row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="visible" title="会员信息" width="520">
      <el-form :model="form" label-width="90px">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" /></el-form-item>
        <el-form-item label="昵称"><el-input v-model="form.nickname" /></el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
        <el-form-item label="等级">
          <el-select v-model="form.levelName">
            <el-option v-for="item in levels" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="积分"><el-input-number v-model="form.points" :min="0" /></el-form-item>
        <el-form-item label="消费"><el-input-number v-model="form.totalSpend" :min="0" /></el-form-item>
        <el-form-item label="状态">
          <el-radio-group v-model="form.status">
            <el-radio-button label="正常" />
            <el-radio-button label="冻结" />
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
import { customerApi } from '../api/modules'

const rows = ref([])
const visible = ref(false)
const form = reactive({})
const levels = ['普通会员', '黄金会员', '铂金会员', '钻石会员']

async function load() { rows.value = await customerApi.list() }

function create() {
  Object.assign(form, {
    id: null, username: '', password: '123456', nickname: '',
    phone: '', levelName: '普通会员', points: 0, totalSpend: 0, status: '正常'
  })
  visible.value = true
}

function edit(row) { Object.assign(form, row); visible.value = true }

async function save() {
  form.id ? await customerApi.update(form.id, form) : await customerApi.create(form)
  visible.value = false
  load()
}

onMounted(load)
</script>
