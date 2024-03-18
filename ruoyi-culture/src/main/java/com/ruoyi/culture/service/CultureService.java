package com.ruoyi.culture.service;

import com.ruoyi.culture.domain.Culture;

import java.util.List;

/**
 * 非遗文化管理 Service 接口
 *
 * @Author fanjaixing
 * @Date 2024/3/18 21:10
 */
public interface CultureService {
    /**
     * 获取所有非遗文化
     *
     * @return 所有非遗文化列表
     */
    List<Culture> getAllCultures();

    /**
     * 根据非遗ID获取非遗文化信息
     *
     * @param cultureId 非遗ID
     * @return 非遗文化信息
     */
    Culture getCultureById(Long cultureId);

    /**
     * 添加非遗文化
     *
     * @param culture 待添加的非遗文化信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addCulture(Culture culture);

    /**
     * 更新非遗文化信息
     *
     * @param culture 待更新的非遗文化信息
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updateCulture(Culture culture);

    /**
     * 删除非遗文化
     *
     * @param cultureId 待删除的非遗文化ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteCulture(Long cultureId);
}
