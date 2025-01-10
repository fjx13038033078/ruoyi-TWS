package com.ruoyi.student.mapper;

import com.ruoyi.student.domain.Grade;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

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
     * 根据学生ID获取成绩列表
     *
     * @param userId 课程ID
     * @return 成绩列表
     */
    List<Grade> getGradeByUserId(Long userId);

    /**
     * 获取每门课程的挂科率（按课程ID统计）
     * @return 课程ID和挂科率
     */
    @MapKey("courseId")
    List<Map<Long, Object>> getFailureRateByCourse();

    /**
     * 获取每门课程的平均成绩（按课程ID统计）
     * @return 课程ID和平均成绩
     */
    @MapKey("courseId")
    List<Map<Long, Object>> getAverageScoreByCourse();


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
