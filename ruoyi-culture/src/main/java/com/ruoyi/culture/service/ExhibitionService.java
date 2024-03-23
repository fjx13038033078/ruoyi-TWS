package com.ruoyi.culture.service;

import com.ruoyi.culture.domain.Exhibition;

import java.util.List;
import java.util.Map;

/**
 * 非遗展览管理 Service 接口
 *
 * @Author fanjaixing
 * @Date 2024/3/18 21:11
 */
public interface ExhibitionService {
    /**
     * 获取所有非遗展览
     *
     * @return 所有非遗展览列表
     */
    List<Exhibition> getAllExhibitions();

    /**
     * 根据展览ID获取展览信息
     *
     * @param exhibitionId 展览ID
     * @return 展览信息
     */
    Exhibition getExhibitionById(Long exhibitionId);

    /**
     * 添加非遗展览
     *
     * @param exhibition 待添加的非遗展览信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addExhibition(Exhibition exhibition);

    /**
     * 更新非遗展览信息
     *
     * @param exhibition 待更新的非遗展览信息
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateExhibition(Exhibition exhibition);

    /**
     * 删除非遗展览
     *
     * @param exhibitionId 待删除的非遗展览ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteExhibition(Long exhibitionId);


    /**
     * 获取每个非遗及其对应的非遗展览数量的映射关系。
     * 遍历所有非遗，查询每个非遗对应的非遗展览数量，构建非遗名称与展览数量的映射关系。
     *
     * @return 包含每个非遗及其对应的非遗展览数量的映射关系的 Map 对象
     */
    public Map<String, Integer> getCultureExhibitionCountMap();
}
