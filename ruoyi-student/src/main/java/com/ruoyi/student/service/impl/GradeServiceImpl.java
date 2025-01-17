package com.ruoyi.student.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.student.domain.Course;
import com.ruoyi.student.domain.Grade;
import com.ruoyi.student.mapper.GradeMapper;
import com.ruoyi.student.service.CourseService;
import com.ruoyi.student.service.GradeService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.utils.PageUtils.startPage;

/**
 * @Author 范佳兴
 * @date 2024/12/5 9:58
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeMapper gradeMapper;

    private final ISysRoleService iSysRoleService;

    private final ISysUserService iSysUserService;

    private final CourseService courseService;
    private final ConversionService conversionService;

    @Transactional
    @Override
    public List<Grade> getAllGrades() {
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equalsIgnoreCase("admin") || role.equalsIgnoreCase("teacher")) {
            startPage();
            List<Grade> allGrades = gradeMapper.getAllGrades();
            fillStudentName(allGrades);
            fillCourseNameAndSemester(allGrades);
            return allGrades;
        } else {
            startPage();
            List<Grade> gradeByUserId = gradeMapper.getGradeByUserId(userId);
            fillStudentName(gradeByUserId);
            fillCourseNameAndSemester(gradeByUserId);
            return gradeByUserId;
        }
    }

    @Override
    public Grade getGradeById(Long gradeId) {
        return gradeMapper.getGradeById(gradeId);
    }

    @Override
    public Map<String, Double> getFailureRateByCourse() {
        log.info("getFailureRateByCourse",gradeMapper.getFailureRateByCourse());
        // 1. 获取每个课程的挂科率
        List<Map<Long, Object>> failureRateList = gradeMapper.getFailureRateByCourse();

        // 2. 获取所有课程信息（课程ID与名称的映射）
        List<Course> courses = courseService.getAllCourses();
        Map<Long, String> courseIdToNameMap = new HashMap<>();
        for (Course course : courses) {
            courseIdToNameMap.put(course.getCourseId(), course.getCourseName());
        }

        // 3. 创建一个Map来存储课程名称和对应的挂科率
        Map<String, Double> failureRateMap = new HashMap<>();

        // 4. 直接使用查询结果中的 failureRate 字段，并将其放入 Map 中
        for (Map<Long, Object> entry : failureRateList) {
            Long courseId = (Long) entry.get("courseId");
            BigDecimal failureRateBigDecimal  = (BigDecimal) entry.get("failureRate");
            double failureRate = failureRateBigDecimal.doubleValue();

            // 获取课程名称
            String courseName = courseIdToNameMap.get(courseId);

            // 将结果放入Map中
            failureRateMap.put(courseName, failureRate);
        }

        return failureRateMap;
    }

    @Override
    public Map<String, Double> getAverageScoreByCourse() {
        List<Map<Long, Object>> averageScoreList = gradeMapper.getAverageScoreByCourse();
        log.info("getAverageScoreByCourse{}",averageScoreList);
        List<Course> courses = courseService.getAllCourses();
        Map<Long, String> courseIdToNameMap = new HashMap<>();
        for (Course course : courses) {
            courseIdToNameMap.put(course.getCourseId(), course.getCourseName());
        }

        Map<String, Double> averageScoreMap = new HashMap<>();

        for (Map<Long, Object> entry : averageScoreList) {
            Long courseId = (Long) entry.get("courseId");
            double failureRate  = (Double) entry.get("averageScore");
            // 获取课程名称
            String courseName = courseIdToNameMap.get(courseId);
            // 将结果放入Map中
            averageScoreMap.put(courseName, failureRate);
        }

        return averageScoreMap;
    }

    @Override
    public boolean addGrade(Grade grade) {
        int rows = gradeMapper.addGrade(grade);
        return rows > 0;
    }

    @Override
    public boolean updateGrade(Grade grade) {
        int rows = gradeMapper.updateGrade(grade);
        return rows > 0;
    }

    @Override
    public boolean deleteGrade(Long gradeId) {
        int rows = gradeMapper.deleteGrade(gradeId);
        return rows > 0;
    }

    /**
     * 填充课程名称
     * @param grades
     */
    private void fillCourseNameAndSemester(List<Grade> grades) {
        for (Grade grade : grades) {
            Long courseId = grade.getCourseId();
            Course courseById = courseService.getCourseById(courseId);
            if (courseById == null) {
                grade.setCourseName("该课程已被删除");
                grade.setSemester("该课程已被删除");
            } else {
                grade.setCourseName(courseById.getCourseName());
                grade.setSemester(courseById.getSemester());
            }
        }
    }

    /**
     * 填充学生姓名
     * @param grades
     */
    private void fillStudentName(List<Grade> grades) {
        for (Grade grade : grades) {
            Long studentId = grade.getUserId();
            SysUser sysUser = iSysUserService.selectUserById(studentId);
            grade.setUserName(sysUser.getNickName());
        }
    }
}
