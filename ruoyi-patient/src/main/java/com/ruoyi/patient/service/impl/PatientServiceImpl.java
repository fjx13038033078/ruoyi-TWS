package com.ruoyi.patient.service.impl;

import com.ruoyi.patient.domain.Patient;
import com.ruoyi.patient.mapper.PatientMapper;
import com.ruoyi.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/3/11 9:54
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PatientServiceImpl implements PatientService {

    private final PatientMapper patientMapper; // 依赖注入 Mapper

    /**
     * 获取所有患者登记信息
     *
     * @return 患者登记列表
     */
    @Override
    public List<Patient> getAllPatients() {
        log.info("获取所有患者登记信息:",patientMapper.getAllPatients());
        return patientMapper.getAllPatients();
    }

    /**
     * 根据 ID 获取患者登记信息
     *
     * @param id 患者 ID
     * @return 患者信息
     */
    @Override
    public Patient getPatientById(Long id) {
        return patientMapper.getPatientById(id);
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
        int rows = patientMapper.addPatient(patient);
        return rows > 0;
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
}
