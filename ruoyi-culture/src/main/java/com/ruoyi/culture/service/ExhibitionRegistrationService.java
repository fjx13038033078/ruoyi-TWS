package com.ruoyi.culture.service;

import com.ruoyi.culture.domain.ExhibitionRegistration;

import java.util.List;

/**
 * 非遗展览报名管理 Service 接口
 *
 * @Author fanjaixing
 * @Date 2024/3/18 21:12
 */
public interface ExhibitionRegistrationService {

    /**
     * 获取所有非遗展览报名信息
     *
     * @return 所有非遗展览报名信息列表
     */
    List<ExhibitionRegistration> getAllExhibitionRegistrations();

    /**
     * 根据报名ID获取展览报名信息
     *
     * @param registrationId 报名ID
     * @return 展览报名信息
     */
    ExhibitionRegistration getExhibitionRegistrationById(Long registrationId);

    /**
     * 添加非遗展览报名信息
     *
     * @param registration 待添加的非遗展览报名信息
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addExhibitionRegistration(ExhibitionRegistration registration);

    /**
     * 删除非遗展览报名信息
     *
     * @param registrationId 待删除的非遗展览报名ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deleteExhibitionRegistration(Long registrationId);
}
