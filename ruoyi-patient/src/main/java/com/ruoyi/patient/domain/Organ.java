package com.ruoyi.patient.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @Author 范佳兴
 * @date 2025/3/11 10:02
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TableName("tws_organ")
public class Organ implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键，器官 ID
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 器官类型（0: 肝, 1: 肾, 2: 心脏等）
     */
    private Integer organType;

    /**
     * 血型（0: A, 1: B, 2: AB, 3: O）
     */
    private Integer bloodType;

    /**
     * 健康状况描述
     */
    private String healthCondition;

    /**
     * 是否已匹配（0: 否, 1: 是）
     */
    private Integer matched;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;
}
