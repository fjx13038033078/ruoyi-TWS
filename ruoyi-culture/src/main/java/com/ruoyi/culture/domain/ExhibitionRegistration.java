package com.ruoyi.culture.domain;

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
     * 展览ID
     */
    private Long exhibitionId;

    /**
     * 报名时间，默认为当前时间
     */
    private LocalDateTime registrationTime;

    /**
     * 序列化版本UID
     */
    private static final long serialVersionUID = 1L;
}
