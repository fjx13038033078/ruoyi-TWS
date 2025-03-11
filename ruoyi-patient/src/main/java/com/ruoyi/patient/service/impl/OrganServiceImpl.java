package com.ruoyi.patient.service.impl;

import com.ruoyi.patient.domain.Organ;
import com.ruoyi.patient.mapper.OrganMapper;
import com.ruoyi.patient.service.OrganService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/3/11 10:08
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class OrganServiceImpl implements OrganService {

    private final OrganMapper organMapper; // 依赖注入 Mapper

    /**
     * 获取所有器官供体信息
     *
     * @return 器官供体列表
     */
    @Override
    public List<Organ> getAllOrgans() {
        log.info("获取所有器官供体信息:",organMapper.getAllOrgans());
        return organMapper.getAllOrgans();
    }

    /**
     * 根据 ID 获取器官信息
     *
     * @param id 器官 ID
     * @return 器官信息
     */
    @Override
    public Organ getOrganById(Long id) {
        return organMapper.getOrganById(id);
    }

    /**
     * 添加器官供体信息
     *
     * @param organ 器官对象
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addOrgan(Organ organ) {
        int rows = organMapper.addOrgan(organ);
        return rows > 0;
    }

    /**
     * 更新器官供体信息
     *
     * @param organ 器官对象
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateOrgan(Organ organ) {
        int rows = organMapper.updateOrgan(organ);
        return rows > 0;
    }

    /**
     * 删除器官供体信息
     *
     * @param id 器官 ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteOrgan(Long id) {
        int rows = organMapper.deleteOrgan(id);
        return rows > 0;
    }
}
