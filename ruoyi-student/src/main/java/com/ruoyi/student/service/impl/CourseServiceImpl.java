package com.ruoyi.student.service.impl;

import com.ruoyi.student.domain.Course;
import com.ruoyi.student.domain.Grade;
import com.ruoyi.student.mapper.CourseMapper;
import com.ruoyi.student.mapper.GradeMapper;
import com.ruoyi.student.service.CourseService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 *
 * CourseService 和 GradeService 实现类
 *
 * @Author 范佳兴
 * @date 2024/12/5 9:56
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseMapper courseMapper;
    private final GradeMapper gradeMapper;

    @Override
    public List<Course> getAllCourses() {
        return courseMapper.getAllCourses();
    }

    @Override
    public Course getCourseById(Long courseId) {
        return courseMapper.getCourseById(courseId);
    }

    @Override
    public boolean addCourse(Course course) {
        int rows = courseMapper.addCourse(course);
        return rows > 0;
    }

    @Override
    public boolean updateCourse(Course course) {
        int rows = courseMapper.updateCourse(course);
        return rows > 0;
    }

    @Override
    public boolean deleteCourse(Long courseId) {
        gradeMapper.deleteGrade(courseId);
        int rows = courseMapper.deleteCourse(courseId);
        return rows > 0;
    }
}
