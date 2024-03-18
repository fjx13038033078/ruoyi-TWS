package com.ruoyi.culture.service.impl;

import com.ruoyi.culture.domain.ExhibitionRegistration;
import com.ruoyi.culture.mapper.ExhibitionRegistrationMapper;
import com.ruoyi.culture.service.ExhibitionRegistrationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 非遗展览报名管理 Service 实现类
 *
 * @Author fanjaixing
 * @Date 2024/3/18 21:17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExhibitionRegistrationServiceImpl implements ExhibitionRegistrationService {
    private final ExhibitionRegistrationMapper exhibitionRegistrationMapper;

    /**
     * 获取所有非遗展览报名信息
     *
     * @return 所有非遗展览报名信息列表
     */
    @Override
    public List<ExhibitionRegistration> getAllExhibitionRegistrations() {
        return exhibitionRegistrationMapper.getAllExhibitionRegistrations();
    }

    /**
     * 根据报名ID获取展览报名信息
     *
     * @param registrationId 报名ID
     * @return 展览报名信息
     */
    @Override
    public ExhibitionRegistration getExhibitionRegistrationById(Long registrationId) {
        return exhibitionRegistrationMapper.getExhibitionRegistrationById(registrationId);
    }

    /**
     * 添加非遗展览报名信息
     *
     * @param registration 待添加的非遗展览报名信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addExhibitionRegistration(ExhibitionRegistration registration) {
        int rows = exhibitionRegistrationMapper.addExhibitionRegistration(registration);
        return rows > 0;
    }

    /**
     * 删除非遗展览报名信息
     *
     * @param registrationId 待删除的非遗展览报名ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteExhibitionRegistration(Long registrationId) {
        int rows = exhibitionRegistrationMapper.deleteExhibitionRegistration(registrationId);
        return rows > 0;
    }
}
