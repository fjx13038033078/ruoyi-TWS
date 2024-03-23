import request from '@/utils/request'

// 查询所有非遗展览列表
export function listAllExhibitions(query) {
  return request({
    url: '/culture/exhibition/listAll',
    method: 'get',
    params: query
  })
}

// 查询非遗展览详细信息
export function getExhibitionById(exhibitionId) {
  return request({
    url: '/culture/exhibition/detail',
    method: 'get',
    params: { exhibitionId }
  })
}

// 添加非遗展览
export function addExhibition(data) {
  return request({
    url: '/culture/exhibition/add',
    method: 'post',
    data: data
  })
}

// 更新非遗展览信息
export function updateExhibition(data) {
  return request({
    url: '/culture/exhibition/update',
    method: 'post',
    data: data
  })
}

// 删除非遗展览
export function deleteExhibition(exhibitionId) {
  return request({
    url: '/culture/exhibition/delete',
    method: 'get',
    params: { exhibitionId }
  })
}

// 查询场馆与场地数量的映射
export function getCultureExhibitionCountMap() {
  return request({
    url: '/culture/exhibition/getMap',
    method: 'get'
  })
}
