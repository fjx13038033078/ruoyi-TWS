package com.ruoyi.patient.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;
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

    /** 登记 ID，主键 */
    @TableId(type = IdType.AUTO)
    private Long id;

    /** 患者 ID，外键关联用户表 */
    private Long userId;

    @TableField(exist = false)
    private String userName;

    /** 所需器官（0: 肝, 1: 肾） */
    private Integer organNeeded;

    /** 血型（0: A, 1: B, 2: AB, 3: O） */
    private Integer bloodType;

    /** 病情是否紧急（0-否，1-是） */
    private Integer emergencyStatus;

    /** 状态（0: 审核中, 1: 等待移植, 2: 准备移植, 3: 完成移植, 4: 已放弃） */
    private Integer status;

    /** HLA 配型信息 */
    private String hlaTyping;

    /** 是否感染乙肝病毒（0: 否, 1: 是） */
    private Integer hbvInfected;

    /** 是否感染丙肝病毒（0: 否, 1: 是） */
    private Integer hcvInfected;

    /** 是否感染艾滋病病毒（0: 否, 1: 是） */
    private Integer hivInfected;

    /** 医疗状态（0: 在重症监护室, 1: 住院, 2: 不在重症监护室, 3: 未住院） */
    private Integer medicalStatus;

    /** 自评状态（0: 病情稳定, 1: 病情反复, 2: 情况危殆） */
    private Integer selfAssessment;

    /** 病情描述 */
    private String medicalCondition;

    /** 创建时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime createTime;

    /** 是否正在透析（0: 否, 1: 是，仅适用于肾移植） */
    private Integer isOnDialysis;

    /** 透析类型（0: 血液透析, 1: 腹膜透析，仅适用于肾移植） */
    private Integer dialysisType;

    /** 初次透析时间，仅适用于肾移植 */
    private LocalDate firstDialysisDate;

    /** 透析频率（如每周3次，仅适用于肾移植） */
    private String dialysisFrequency;
}
