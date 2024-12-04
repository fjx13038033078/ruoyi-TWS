package com.ruoyi.student.service;

import com.ruoyi.student.domain.Grade;

import java.util.List;

/**
 *
 * 成绩管理 Service 接口
 *
 * @Author 范佳兴
 * @date 2024/12/4 17:29
 */
public interface GradeService {
    /**
     * 获取所有成绩
     *
     * @return 所有成绩列表
     */
    List<Grade> getAllGrades();

    /**
     * 根据成绩ID获取成绩信息
     *
     * @param gradeId 成绩ID
     * @return 成绩信息
     */
    Grade getGradeById(Long gradeId);

    /**
     * 添加成绩
     *
     * @param grade 待添加的成绩信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addGrade(Grade grade);

    /**
     * 更新成绩信息
     *
     * @param grade 待更新的成绩信息
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateGrade(Grade grade);

    /**
     * 删除成绩
     *
     * @param gradeId 待删除的成绩ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteGrade(Long gradeId);
}
