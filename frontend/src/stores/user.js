import { defineStore } from 'pinia'
import { authApi } from '../api/modules'

export const useUserStore = defineStore('user', {
  state: () => ({
    token: localStorage.getItem('mall_token') || '',
    profile: JSON.parse(localStorage.getItem('mall_user') || 'null')
  }),
  actions: {
    async login(form) {
      const data = await authApi.login(form)
      this.token = data.token
      this.profile = data.user
      localStorage.setItem('mall_token', data.token)
      localStorage.setItem('mall_user', JSON.stringify(data.user))
    },
    logout() {
      this.token = ''
      this.profile = null
      localStorage.removeItem('mall_token')
      localStorage.removeItem('mall_user')
    }
  }
})

