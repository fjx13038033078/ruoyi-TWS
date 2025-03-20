import request from '@/utils/request'

// 查询所有患者登记列表
export function listAllPatients(query) {
  return request({
    url: '/patient/listAll',
    method: 'get',
    params: query
  })
}

// 查询患者详细信息
export function getPatientById(patientId) {
  return request({
    url: '/patient/detail',
    method: 'get',
    params: { patientId }
  })
}

// 统计紧急状态的患者人数
export function countEmergencyPatients() {
  return request({
    url: '/patient/countEmergencyPatients',
    method: 'get'
  })
}

// 统计按器官需求分类的患者人数
export function countPatientsByOrganNeeded() {
  return request({
    url: '/patient/countPatientsByOrganNeeded',
    method: 'get'
  })
}

// 添加患者登记信息
export function addPatient(data) {
  return request({
    url: '/patient/add',
    method: 'post',
    data: data
  })
}

// 更新患者登记信息
export function updatePatient(data) {
  return request({
    url: '/patient/update',
    method: 'post',
    data: data
  })
}

// 删除患者登记信息
export function deletePatient(patientId) {
  return request({
    url: '/patient/delete',
    method: 'get',
    params: { patientId }
  })
}
