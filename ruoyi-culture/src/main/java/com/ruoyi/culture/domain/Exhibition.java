package com.ruoyi.culture.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.Getter;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 非遗展览实体类
 *
 * @Author fanjaixing
 * @Date 2024/3/18 20:57
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TableName("ich_exhibition")
public class Exhibition implements Serializable {

    /**
     * 展览ID，主键
     */
    @TableId(type = IdType.AUTO)
    private Long exhibitionId;

    /**
     * 关联的非遗ID
     */
    private Long cultureId;

    /**
     * 展览名称
     */
    private String exhibitionName;

    /**
     * 展览日期
     */
    private LocalDate exhibitionDate;

    /**
     * 展览地点
     */
    private String exhibitionLocation;

    /**
     * 展览描述
     */
    private String exhibitionDescription;

    /**
     * 序列化版本UID
     */
    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

}
