import request from '@/utils/request'

// 查询所有非遗展览报名列表
export function listAllExhibitionRegistrations(query) {
  return request({
    url: '/culture/exhibition/registration/listAll',
    method: 'get',
    params: query
  })
}

// 查询非遗展览报名详细信息
export function getExhibitionRegistrationById(registrationId) {
  return request({
    url: '/culture/exhibition/registration/detail',
    method: 'get',
    params: { registrationId }
  })
}

// 添加非遗展览报名
export function addExhibitionRegistration(data) {
  return request({
    url: '/culture/exhibition/registration/add',
    method: 'post',
    data: data
  })
}

// 删除非遗展览报名
export function deleteExhibitionRegistration(registrationId) {
  return request({
    url: '/culture/exhibition/registration/delete',
    method: 'get',
    params: { registrationId }
  })
}

// 删除非遗展览报名
export function cancelExhibitionReservation(registrationId) {
  return request({
    url: '/culture/exhibition/registration/cancel',
    method: 'get',
    params: { registrationId }
  })
}
