import request from '@/utils/request'

// 查询所有课程列表
export function listAllCourses(query) {
  return request({
    url: '/student/course/listAll',
    method: 'get',
    params: query
  })
}

// 查询课程详细信息
export function getCourseById(courseId) {
  return request({
    url: '/student/course/detail',
    method: 'get',
    params: { courseId }
  })
}

// 添加课程
export function addCourse(data) {
  return request({
    url: '/student/course/add',
    method: 'post',
    data: data
  })
}

// 更新课程信息
export function updateCourse(data) {
  return request({
    url: '/student/course/update',
    method: 'post',
    data: data
  })
}

// 删除课程
export function deleteCourse(courseId) {
  return request({
    url: '/student/course/delete',
    method: 'delete',
    params: { courseId }
  })
}
