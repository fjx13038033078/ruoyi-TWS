package com.ruoyi.culture.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;

/**
 * 非遗文化实体类
 *
 * @Author fanjaixing
 * @Date 2024/3/18 20:56
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TableName("ich_culture")
public class Culture implements Serializable {
    /**
     * 主键，非遗ID
     */
    @TableId(type = IdType.AUTO)
    private Long cultureId;

    /**
     * 非遗名称
     */
    private String cultureName;

    /**
     * 非遗发源地
     */
    private String cultureBirthplace;

    /**
     * 非遗类型
     */
    private Integer cultureType;

    /**
     * 非遗级别
     */
    private Integer cultureLevel;

    /**
     * 非遗传承人
     */
    private String cultureHeir;

    /**
     * 非遗描述
     */
    private String cultureDescription;

    /**
     * 序列化版本UID
     */
    private static final long serialVersionUID = 1L;
}
