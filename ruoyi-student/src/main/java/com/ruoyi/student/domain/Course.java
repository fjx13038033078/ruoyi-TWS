package com.ruoyi.student.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author 范佳兴
 * @date 2024/12/4 17:06
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TableName("sms_course")
public class Course implements Serializable {
    /**
     * 主键，课程ID
     */
    @TableId(type = IdType.AUTO)
    private Long courseId;

    /**
     * 课程名称
     */
    private String courseName;

    /**
     * 课程学分
     */
    private Integer courseCredits;

    /**
     * 课程学时
     */
    private Integer courseHours;

    /**
     * 授课教师ID
     */
    private Long instructorId;

    /**
     * 授课教师姓名
     */
    @TableField(exist = false)
    private String instructorName;

    /**
     * 开课学期
     */
    private String semester;

    /**
     * 课程描述
     */
    private String courseDescription;

    /**
     * 序列化版本UID
     */
    private static final long serialVersionUID = 1L;
}
