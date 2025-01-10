import request from '@/utils/request'

// 查询所有成绩列表
export function listAllGrades(query) {
  return request({
    url: '/student/grade/listAll',
    method: 'get',
    params: query
  })
}

// 查询成绩详细信息
export function getGradeById(gradeId) {
  return request({
    url: '/student/grade/detail',
    method: 'get',
    params: { gradeId }
  })
}

export function getFailureRateByCourse() {
  return request({
    url: '/student/grade/failureRate',
    method: 'get'
  })
}

export function getAverageScoreByCourse() {
  return request({
    url: '/student/grade/averageScore',
    method: 'get'
  })
}

// 添加成绩
export function addGrade(data) {
  return request({
    url: '/student/grade/add',
    method: 'post',
    data: data
  })
}

// 更新成绩信息
export function updateGrade(data) {
  return request({
    url: '/student/grade/update',
    method: 'post',
    data: data
  })
}

// 删除成绩
export function deleteGrade(gradeId) {
  return request({
    url: '/student/grade/delete',
    method: 'get',
    params: { gradeId }
  })
}
