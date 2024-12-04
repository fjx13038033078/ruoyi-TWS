package com.ruoyi.student.mapper;

import com.ruoyi.student.domain.Course;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * 课程管理 Mapper 接口
 *
 * @Author 范佳兴
 * @date 2024/12/4 17:12
 */
@Mapper
public interface CourseMapper {
    /**
     * 获取所有课程
     *
     * @return 所有课程列表
     */
    List<Course> getAllCourses();

    /**
     * 根据课程ID获取课程信息
     *
     * @param courseId 课程ID
     * @return 课程信息
     */
    Course getCourseById(Long courseId);

    /**
     * 添加课程
     *
     * @param course 待添加的课程信息
     * @return 添加成功返回影响的行数，否则返回0
     */
    int addCourse(Course course);

    /**
     * 更新课程信息
     *
     * @param course 待更新的课程信息
     * @return 更新成功返回影响的行数，否则返回0
     */
    int updateCourse(Course course);

    /**
     * 删除课程
     *
     * @param courseId 待删除的课程ID
     * @return 删除成功返回影响的行数，否则返回0
     */
    int deleteCourse(Long courseId);
}
