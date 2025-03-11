package com.ruoyi.patient.mapper;

import com.ruoyi.patient.domain.Organ;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/3/11 10:03
 */
@Mapper
public interface OrganMapper {
    /**
     * 获取所有器官信息
     */
    List<Organ> getAllOrgans();

    /**
     * 根据 ID 获取器官信息
     */
    Organ getOrganById(Long id);

    /**
     * 添加器官信息
     */
    int addOrgan(Organ organ);

    /**
     * 更新器官信息
     */
    int updateOrgan(Organ organ);

    /**
     * 删除器官信息
     */
    int deleteOrgan(Long id);
}
