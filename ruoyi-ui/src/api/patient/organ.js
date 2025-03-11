import request from '@/utils/request'

// 查询所有器官供体列表
export function listAllOrgans(query) {
  return request({
    url: '/organ/listAll',
    method: 'get',
    params: query
  })
}

// 查询器官详细信息
export function getOrganById(id) {
  return request({
    url: '/organ/detail',
    method: 'get',
    params: { id }
  })
}

// 添加器官信息
export function addOrgan(data) {
  return request({
    url: '/organ/add',
    method: 'post',
    data: data
  })
}

// 更新器官信息
export function updateOrgan(data) {
  return request({
    url: '/organ/update',
    method: 'post',
    data: data
  })
}

// 删除器官信息
export function deleteOrgan(id) {
  return request({
    url: '/organ/delete',
    method: 'get',
    params: { id }
  })
}
