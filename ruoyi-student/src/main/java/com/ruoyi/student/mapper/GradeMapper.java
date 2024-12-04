package com.ruoyi.student.mapper;

import com.ruoyi.student.domain.Grade;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 *
 * 成绩管理 Mapper 接口
 *
 * @Author 范佳兴
 * @date 2024/12/4 17:20
 */
@Mapper
public interface GradeMapper {
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
     * @return 添加成功返回影响的行数，否则返回0
     */
    int addGrade(Grade grade);

    /**
     * 更新成绩信息
     *
     * @param grade 待更新的成绩信息
     * @return 更新成功返回影响的行数，否则返回0
     */
    int updateGrade(Grade grade);

    /**
     * 删除成绩
     *
     * @param gradeId 待删除的成绩ID
     * @return 删除成功返回影响的行数，否则返回0
     */
    int deleteGrade(Long gradeId);
}
