import http from './http'

export const authApi = {
  login: (data) => http.post('/auth/login', data)
}

export const dashboardApi = {
  overview: () => http.get('/dashboard/overview'),
  charts: () => http.get('/dashboard/charts')
}

export const productApi = {
  list: () => http.get('/products'),
  create: (data) => http.post('/products', data),
  update: (id, data) => http.put(`/products/${id}`, data),
  remove: (id) => http.delete(`/products/${id}`),
  status: (id, status) => http.patch(`/products/${id}/status?status=${encodeURIComponent(status)}`)
}

export const categoryApi = {
  list: () => http.get('/categories'),
  create: (data) => http.post('/categories', data),
  update: (id, data) => http.put(`/categories/${id}`, data),
  remove: (id) => http.delete(`/categories/${id}`)
}

export const orderApi = {
  list: () => http.get('/orders'),
  status: (id, status) => http.patch(`/orders/${id}/status?status=${encodeURIComponent(status)}`)
}

export const customerApi = {
  list: () => http.get('/customers'),
  create: (data) => http.post('/customers', data),
  update: (id, data) => http.put(`/customers/${id}`, data)
}

export const inventoryApi = {
  list: () => http.get('/inventory'),
  create: (data) => http.post('/inventory', data)
}

export const couponApi = {
  list: () => http.get('/coupons'),
  create: (data) => http.post('/coupons', data),
  update: (id, data) => http.put(`/coupons/${id}`, data)
}

export const userApi = {
  list: () => http.get('/users'),
  create: (data) => http.post('/users', data),
  update: (id, data) => http.put(`/users/${id}`, data)
}

export const shopApi = {
  login: (data) => http.post('/shop/login', data),
  register: (data) => http.post('/shop/register', data),
  createOrder: (data) => http.post('/shop/orders', data),
  orders: (phone) => http.get(`/shop/orders?phone=${encodeURIComponent(phone)}`)
}
