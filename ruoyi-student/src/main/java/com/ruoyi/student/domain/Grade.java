package com.ruoyi.student.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author 范佳兴
 * @date 2024/12/4 17:10
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TableName("sms_grade")
public class Grade implements Serializable {
    /**
     * 主键，成绩ID
     */
    @TableId(type = IdType.AUTO)
    private Long gradeId;

    /**
     * 学生ID，外键关联学生表
     */
    private Long userId;

    /**
     * 课程ID，外键关联课程表
     */
    private Long courseId;

    /**
     * 成绩，支持百分制（0-100）
     */
    private Double grade;

    /**
     * 学期
     */
    private String semester;

    /**
     * 备注
     */
    private String remarks;

    /**
     * 序列化版本UID
     */
    private static final long serialVersionUID = 1L;
}
