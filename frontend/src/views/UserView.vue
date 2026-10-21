<template>
  <div class="panel">
    <div class="section-title">
      <div>
        <h3>用户角色</h3>
        <p>后台管理员、角色权限和登录状态</p>
      </div>
      <el-button type="primary" @click="create">新增管理员</el-button>
    </div>
    <el-table :data="rows" height="650">
      <el-table-column prop="username" label="账号" />
      <el-table-column prop="realName" label="姓名" />
      <el-table-column prop="roleName" label="角色">
        <template #default="{ row }">
          <el-tag type="success">{{ row.roleName }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column prop="phone" label="手机号" />
      <el-table-column prop="status" label="状态" width="100" />
      <el-table-column prop="lastLoginAt" label="最近登录" min-width="170" />
      <el-table-column label="操作" width="100">
        <template #default="{ row }">
          <el-button link type="primary" @click="edit(row)">编辑</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog v-model="visible" title="管理员信息" width="520">
      <el-form :model="form" label-width="90px">
        <el-form-item label="账号"><el-input v-model="form.username" /></el-form-item>
        <el-form-item label="密码"><el-input v-model="form.password" /></el-form-item>
        <el-form-item label="姓名"><el-input v-model="form.realName" /></el-form-item>
        <el-form-item label="角色">
          <el-select v-model="form.roleName">
            <el-option v-for="item in roles" :key="item" :label="item" :value="item" />
          </el-select>
        </el-form-item>
        <el-form-item label="手机号"><el-input v-model="form.phone" /></el-form-item>
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
import { userApi } from '../api/modules'

const rows = ref([])
const visible = ref(false)
const form = reactive({})
const roles = ['超级管理员', '运营经理', '客服专员', '仓库主管', '财务人员']

async function load() { rows.value = await userApi.list() }

function create() {
  Object.assign(form, {
    id: null, username: '', password: '123456', realName: '',
    roleName: '运营经理', phone: '', status: '启用'
  })
  visible.value = true
}

function edit(row) { Object.assign(form, row); visible.value = true }

async function save() {
  form.id ? await userApi.update(form.id, form) : await userApi.create(form)
  visible.value = false
  load()
}

onMounted(load)
</script>
