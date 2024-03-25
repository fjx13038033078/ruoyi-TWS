package com.ruoyi.culture.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.culture.domain.Culture;
import com.ruoyi.culture.domain.Exhibition;
import com.ruoyi.culture.domain.ExhibitionRegistration;
import com.ruoyi.culture.mapper.CultureMapper;
import com.ruoyi.culture.mapper.ExhibitionMapper;
import com.ruoyi.culture.mapper.ExhibitionRegistrationMapper;
import com.ruoyi.culture.service.ExhibitionRegistrationService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

    private final ISysRoleService iSysRoleService;

    private final ISysUserService iSysUserService;

    private final ExhibitionMapper exhibitionMapper;

    /**
     * 获取所有非遗展览报名信息
     *
     * @return 所有非遗展览报名信息列表
     */
    @Override
    public List<ExhibitionRegistration> getAllExhibitionRegistrations() {
        List<ExhibitionRegistration> allExhibitionRegistrations = exhibitionRegistrationMapper.getAllExhibitionRegistrations();
        fillCultureAndExhibitionName(allExhibitionRegistrations);
        return allExhibitionRegistrations;
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
        //获取当前登录的用户ID
        registration.setUserId(SecurityUtils.getUserId());
        registration.setRegistrationTime(LocalDateTime.now());
        registration.setRegistrationStatus(0);
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

    /**
     * 填充预约记录中用户和展览名称
     *
     * @param exhibitionRegistrations 待填充的预约记录列表
     */
    private void fillCultureAndExhibitionName(List<ExhibitionRegistration> exhibitionRegistrations) {
        for (ExhibitionRegistration exhibitionRegistration : exhibitionRegistrations) {
            // 获取用户名称
            Long userId = exhibitionRegistration.getUserId();
            SysUser user = iSysUserService.selectUserById(userId);
            if (user != null) {
                exhibitionRegistration.setUserName(user.getNickName());
            }
            //获取展览名称
            Long exhibitionId = exhibitionRegistration.getExhibitionId();
            String exhibitionName = exhibitionMapper.getExhibitionById(exhibitionId).getExhibitionName();
            exhibitionRegistration.setExhibitionName(exhibitionName);
        }
    }

    /**
     * 取消预约
     *
     * @param reservationId 待取消的非遗展览报名ID
     */
    public void cancelExhibitionReservation(Long reservationId) {
        ExhibitionRegistration exhibitionRegistration = exhibitionRegistrationMapper.getExhibitionRegistrationById(reservationId);
        log.info("exhibitionRegistration: " + exhibitionRegistration);
        if (exhibitionRegistration != null && exhibitionRegistration.getRegistrationStatus() == 1) {
            throw new RuntimeException("已经是取消状态，请勿重复取消");
        } else { // 如果预约状态为预约
            exhibitionRegistration.setRegistrationStatus(1); // 设置预约状态为取消
            exhibitionRegistrationMapper.updateExhibitionRegistration(exhibitionRegistration); // 更新预约信息
        }
    }

}
