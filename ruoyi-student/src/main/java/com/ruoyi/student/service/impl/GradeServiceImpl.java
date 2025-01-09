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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
            grade.setCourseName(courseById.getCourseName());
            grade.setSemester(courseById.getSemester());
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
