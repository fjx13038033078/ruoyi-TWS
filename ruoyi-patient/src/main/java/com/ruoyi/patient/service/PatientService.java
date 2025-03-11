package com.ruoyi.patient.service;

import com.ruoyi.patient.domain.Patient;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/3/11 9:54
 */
public interface PatientService {
    /**
     * 获取所有患者登记信息
     *
     * @return 患者登记列表
     */
    List<Patient> getAllPatients();

    /**
     * 根据 ID 获取患者登记信息
     *
     * @param id 患者 ID
     * @return 患者信息
     */
    Patient getPatientById(Long id);

    /**
     * 添加患者登记信息
     *
     * @param patient 患者对象
     * @return 添加成功返回 true，否则返回 false
     */
    boolean addPatient(Patient patient);

    /**
     * 更新患者登记信息
     *
     * @param patient 患者对象
     * @return 更新成功返回 true，否则返回 false
     */
    boolean updatePatient(Patient patient);

    /**
     * 删除患者登记信息
     *
     * @param id 患者 ID
     * @return 删除成功返回 true，否则返回 false
     */
    boolean deletePatient(Long id);
}
