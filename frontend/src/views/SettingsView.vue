<template>
  <div class="settings-grid">
    <div class="panel">
      <div class="section-title">
        <h3>店铺配置</h3>
        <p>基础资料和客服信息</p>
      </div>
      <el-form label-position="top">
        <el-form-item label="店铺名称"><el-input v-model="settings.shopName" /></el-form-item>
        <el-form-item label="客服电话"><el-input v-model="settings.servicePhone" /></el-form-item>
        <el-form-item label="营业状态">
          <el-switch v-model="settings.open" active-text="营业中" inactive-text="维护中" />
        </el-form-item>
        <el-form-item label="店铺公告">
          <el-input v-model="settings.notice" type="textarea" :rows="4" />
        </el-form-item>
      </el-form>
    </div>

    <div class="panel">
      <div class="section-title">
        <h3>交易配置</h3>
        <p>支付、配送和售后参数</p>
      </div>
      <el-form label-position="top">
        <el-form-item label="免运费门槛">
          <el-input-number v-model="settings.freeShipping" :min="0" />
        </el-form-item>
        <el-form-item label="自动收货天数">
          <el-input-number v-model="settings.autoReceiveDays" :min="1" />
        </el-form-item>
        <el-form-item label="支持支付方式">
          <el-checkbox-group v-model="settings.payments">
            <el-checkbox label="微信支付" />
            <el-checkbox label="支付宝" />
            <el-checkbox label="银行卡" />
          </el-checkbox-group>
        </el-form-item>
        <el-form-item label="售后策略">
          <el-input v-model="settings.afterSale" type="textarea" :rows="4" />
        </el-form-item>
      </el-form>
    </div>

    <div class="panel wide">
      <div class="section-title">
        <h3>运营开关</h3>
        <p>常用能力快速配置</p>
      </div>
      <div class="switch-list">
        <label v-for="item in switches" :key="item.key">
          <span>
            <strong>{{ item.label }}</strong>
            <small>{{ item.desc }}</small>
          </span>
          <el-switch v-model="item.enabled" />
        </label>
      </div>
      <el-button type="primary">保存设置</el-button>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue'

const settings = reactive({
  shopName: '星河商城旗舰店',
  servicePhone: '400-800-2026',
  open: true,
  notice: '夏季大促进行中，订单将在 24 小时内完成发货。',
  freeShipping: 99,
  autoReceiveDays: 10,
  payments: ['微信支付', '支付宝'],
  afterSale: '支持 7 天无理由退货，生鲜商品按实际履约规则处理。'
})

const switches = reactive([
  { key: 'flash', label: '限时秒杀', desc: '开启首页秒杀频道', enabled: true },
  { key: 'invoice', label: '电子发票', desc: '订单完成后可申请开票', enabled: true },
  { key: 'risk', label: '风控拦截', desc: '异常订单进入人工审核', enabled: true },
  { key: 'points', label: '积分抵扣', desc: '会员下单可使用积分', enabled: false }
])
</script>
