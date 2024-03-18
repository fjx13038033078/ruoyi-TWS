import request from '@/utils/request'

// 查询所有非遗文化列表
export function listAllCultures(query) {
  return request({
    url: '/culture/listAll',
    method: 'get',
    params: query
  })
}

// 查询非遗文化详细信息
export function getCultureById(cultureId) {
  return request({
    url: '/culture/detail',
    method: 'get',
    params: { cultureId }
  })
}

// 添加非遗文化
export function addCulture(data) {
  return request({
    url: '/culture/add',
    method: 'post',
    data: data
  })
}

// 更新非遗文化信息
export function updateCulture(data) {
  return request({
    url: '/culture/update',
    method: 'post',
    data: data
  })
}

// 删除非遗文化
export function deleteCulture(cultureId) {
  return request({
    url: '/culture/delete',
    method: 'delete',
    params: { cultureId }
  })
}
