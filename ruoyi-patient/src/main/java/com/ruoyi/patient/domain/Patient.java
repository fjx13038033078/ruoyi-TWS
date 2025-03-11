package com.ruoyi.patient.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author 范佳兴
 * @date 2025/3/11 9:48
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TableName("tws_patient")
public class Patient implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 主键，登记 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 患者 ID，外键关联用户表
     */
    private Long userId;

    /**
     * 所需移植的器官（0: 肝, 1: 肾, 2: 心脏等）
     */
    private Integer organNeeded;

    /**
     * 血型（0: A, 1: B, 2: AB, 3: O）
     */
    private Integer bloodType;

    /**
     * 病情描述
     */
    private String medicalCondition;

    /**
     * 紧急状态（0: 否, 1: 是）
     */
    private Integer emergencyStatus;

    /**
     * 当前状态（0: 审核中, 1: 等待移植, 2: 准备移植, 3: 完成移植, 4: 已放弃）
     */
    private Integer status;

    /**
     * 创建时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;
}
