package com.ruoyi.patient.service;

import com.ruoyi.patient.domain.Organ;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/3/11 10:07
 */
public interface OrganService {
    /**
     * 获取所有器官供体信息
     *
     * @return 器官供体列表
     */
    List<Organ> getAllOrgans();

    /**
     * 根据 ID 获取器官信息
     *
     * @param id 器官 ID
     * @return 器官信息
     */
    Organ getOrganById(Long id);

    /**
     * 添加器官供体信息
     *
     * @param organ 器官对象
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addOrgan(Organ organ);

    /**
     * 更新器官供体信息
     *
     * @param organ 器官对象
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateOrgan(Organ organ);

    /**
     * 删除器官供体信息
     *
     * @param id 器官 ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteOrgan(Long id);
}
