package com.ruoyi.patient.service.impl;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.patient.domain.Patient;
import com.ruoyi.patient.mapper.PatientMapper;
import com.ruoyi.patient.service.PatientService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @Author 范佳兴
 * @date 2025/3/11 9:54
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientMapper patientMapper;

    private final ISysUserService iSysUserService;

    private final ISysRoleService iSysRoleService;

    /**
     * 获取所有患者登记信息
     *
     * @return 患者登记列表
     */
    @Override
    public List<Patient> getAllPatients(Patient patient) {
        log.info("获取所有患者登记信息:",patient);
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equals("admin")) {
            List<Patient> allPatients = patientMapper.getAllPatients(patient);
            fillPatientInfo(allPatients);
            return allPatients;
        } else {
            List<Patient> patientByUserId = patientMapper.getPatientByUserId(userId);
            fillPatientInfo(patientByUserId);
            return patientByUserId;
        }
    }

    /**
     * 根据 ID 获取患者登记信息
     *
     * @param id 患者 ID
     * @return 患者信息
     */
    @Override
    public Patient getPatientById(Long id) {
        Patient patientById = patientMapper.getPatientById(id);
        String nickName = iSysUserService.selectUserById(patientById.getUserId()).getNickName();
        patientById.setUserName(nickName);
        return patientById;
    }

    @Override
    public int countEmergencyPatients() {
        return patientMapper.countEmergencyPatients();
    }

    @Override
    public Map<String, Integer> countPatientsByOrganNeeded() {
        return patientMapper.countPatientsByOrganNeeded();
    }

    /**
     * 添加患者登记信息
     *
     * @param patient 患者对象
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addPatient(Patient patient) {
        patient.setCreateTime(LocalDateTime.now());
        Long userId = SecurityUtils.getUserId();
        String role = iSysRoleService.selectStringRoleByUserId(userId);
        if (role.equals("admin")) {
            int rows = patientMapper.addPatient(patient);
            return rows > 0;
        } else {
            patient.setUserId(userId);
            int rows = patientMapper.addPatient(patient);
            return rows > 0;
        }
    }

    /**
     * 更新患者登记信息
     *
     * @param patient 患者对象
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updatePatient(Patient patient) {
        int rows = patientMapper.updatePatient(patient);
        return rows > 0;
    }

    /**
     * 删除患者登记信息
     *
     * @param id 患者 ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deletePatient(Long id) {
        int rows = patientMapper.deletePatient(id);
        return rows > 0;
    }

    private void fillPatientInfo(List<Patient> patients) {
        for (Patient patient : patients) {
            Long userId = patient.getUserId();
            String nickName = iSysUserService.selectUserById(userId).getNickName() != null ? iSysUserService.selectUserById(userId).getNickName() : "";
            patient.setUserName(nickName);
        }
    }
}
