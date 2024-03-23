package com.ruoyi.culture.domain;

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
 * @Author fanjaixing
 * @Date 2024/3/18 20:58
 */
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@TableName("ich_exhibition_registration")
public class ExhibitionRegistration implements Serializable {

    /**
     * 报名ID，主键
     */
    @TableId(type = IdType.AUTO)
    private Long registrationId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户姓名
     */
    private String userName;

    /**
     * 展览ID
     */
    private Long exhibitionId;

    /**
     * 展览名称
     */
    private String exhibitionName;

    /**
     * 报名时间，默认为当前时间
     */
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime registrationTime;

    /**
     * 预约状态，0预约，1取消
     */
    private Integer registrationStatus;

    /**
     * 序列化版本UID
     */
    private static final long serialVersionUID = 1L;
}
