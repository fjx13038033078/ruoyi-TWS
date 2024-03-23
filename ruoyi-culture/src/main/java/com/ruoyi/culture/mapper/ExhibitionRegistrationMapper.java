package com.ruoyi.culture.mapper;

import com.ruoyi.culture.domain.ExhibitionRegistration;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 非遗展览报名管理 Mapper 接口
 *
 * @Author fanjaixing
 * @Date 2024/3/18 21:04
 */
@Mapper
public interface ExhibitionRegistrationMapper {

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
     * @return 添加成功返回影响的行数，否则返回0
     */
    int addExhibitionRegistration(ExhibitionRegistration registration);

    /**
     * 删除非遗展览报名信息
     *
     * @param registrationId 待删除的非遗展览报名ID
     * @return 删除成功返回影响的行数，否则返回0
     */
    int deleteExhibitionRegistration(Long registrationId);

    /**
     * 更新预约信息
     *
     * @param exhibitionRegistration 待更新的非遗展览报名信息
     * @return
     */
    int updateExhibitionRegistration(ExhibitionRegistration exhibitionRegistration);
}
