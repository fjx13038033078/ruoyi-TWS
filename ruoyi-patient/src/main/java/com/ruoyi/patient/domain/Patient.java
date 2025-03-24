package com.ruoyi.patient.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * @Author 大黄蜂
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
     * 登记 ID，主键
     */
    @TableId(type = IdType.AUTO)
    @Excel(name = "登记ID")
    private Long id;

    /**
     * 患者 ID，外键关联用户表
     */
    @Excel(name = "患者ID", type = Excel.Type.IMPORT)
    private Long userId;

    @TableField(exist = false)
    @Excel(name = "患者姓名", type = Excel.Type.EXPORT)
    private String userName;

    /**
     * 所需器官（0: 肝, 1: 肾）
     */
    @Excel(name = "所需器官", readConverterExp = "0=肝,1=肾")
    private Integer organNeeded;

    /**
     * 血型（0: A, 1: B, 2: AB, 3: O）
     */
    @Excel(name = "血型", readConverterExp = "0=A,1=B,2=AB,3=O")
    private Integer bloodType;

    /**
     * 病情是否紧急（0-否，1-是）
     */
    @Excel(name = "病情是否紧急", readConverterExp = "0=否,1=是")
    private Integer emergencyStatus;

    /**
     * 状态（0: 审核中, 1: 等待移植, 2: 准备移植, 3: 完成移植, 4: 已放弃）
     */
    @Excel(name = "状态", readConverterExp = "1=等待移植,2=准备移植,3=完成移植,4=已放弃")
    private Integer status;

    /**
     * HLA 配型信息
     */
    @Excel(name = "HLA 配型信息")
    private String hlaTyping;

    /**
     * 是否感染乙肝病毒（0: 否, 1: 是）
     */
    @Excel(name = "是否感染乙肝病毒", readConverterExp = "0=否,1=是")
    private Integer hbvInfected;

    /**
     * 是否感染丙肝病毒（0: 否, 1: 是）
     */
    @Excel(name = "是否感染丙肝病毒", readConverterExp = "0=否,1=是")
    private Integer hcvInfected;

    /**
     * 是否感染艾滋病病毒（0: 否, 1: 是）
     */
    @Excel(name = "是否感染艾滋病病毒", readConverterExp = "0=否,1=是")
    private Integer hivInfected;

    /**
     * 医疗状态（0: 在重症监护室, 1: 住院, 2: 不在重症监护室, 3: 未住院）
     */
    @Excel(name = "医疗状态", readConverterExp = "0=在重症监护室,1=住院,2=不在重症监护室,3=未住院")
    private Integer medicalStatus;

    /**
     * 自评状态（0: 病情稳定, 1: 病情反复, 2: 情况危殆）
     */
    @Excel(name = "自评状态", readConverterExp = "0=病情稳定,1=病情反复,2=情况危殆")
    private Integer selfAssessment;

    /**
     * 病情描述
     */
    @Excel(name = "病情描述")
    private String medicalCondition;

    /**
     * 创建时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    @Excel(name = "创建时间",type = Excel.Type.EXPORT)
    private LocalDateTime createTime;

    /**
     * 是否正在透析（0: 否, 1: 是，仅适用于肾移植）
     */
    @Excel(name = "是否正在透析", readConverterExp = "0=否, 1=是")
    private Integer isOnDialysis;

    /**
     * 透析类型（0: 血液透析, 1: 腹膜透析，仅适用于肾移植）
     */
    @Excel(name = "透析类型", readConverterExp = "0=血液透析,1=腹膜透析")
    private Integer dialysisType;

    /**
     * 初次透析时间，仅适用于肾移植
     */
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    @Excel(name = "初次透析时间")
    private Date firstDialysisDate;

    /**
     * 透析频率（如每周3次，仅适用于肾移植）
     */
    @Excel(name = "透析频率")
    private String dialysisFrequency;

    /**
     * 患者上传的文件
     */
    private String fileName;

    /**
     * 肌酐（umol/L）
     */
    @Excel(name = "肌酐（umol/L）")
    private BigDecimal creatinine;

    /**
     * 谷丙转氨酶（ALT，U/L）
     */
    @Excel(name = "谷丙转氨酶（ALT，U/L）")
    private BigDecimal alt;

    /**
     * 谷草转氨酶（AST，U/L）
     */
    @Excel(name = "谷草转氨酶（AST，U/L）")
    private BigDecimal ast;

    /**
     * 是否患有白血病（0: 否, 1: 是）
     */
    @Excel(name = "是否患有白血病", readConverterExp = "0=否,1=是")
    private Integer leukemia;

    /**
     * 过去一年内是否曾骨折（0: 否, 1: 是）
     */
    @Excel(name = "过去一年内是否曾骨折", readConverterExp = "0=否,1=是")
    private Integer fractureLastYear;

    /**
     * 是否患有血管性骨坏死（0: 否, 1: 是）
     */
    @Excel(name = "是否患有血管性骨坏死", readConverterExp = "0=否,1=是")
    private Integer avascularNecrosis;
}
