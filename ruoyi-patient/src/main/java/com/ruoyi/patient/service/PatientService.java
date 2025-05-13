package com.ruoyi.patient.service;

import com.ruoyi.patient.domain.Patient;

import java.util.List;
import java.util.Map;

/**
 * @Author 大黄蜂
 * @date 2025/3/11 9:54
 */
public interface PatientService {
    /**
     * 获取所有患者登记信息
     *
     * @return 患者登记列表
     */
    List<Patient> getAllPatients(Patient patient);

    /**
     * 根据 ID 获取患者登记信息
     *
     * @param id 患者 ID
     * @return 患者信息
     */
    Patient getPatientById(Long id);

    /**
     * 获取紧急患者数量
     *
     * @return 紧急患者数量
     */
    int countEmergencyPatients();

    int countPatients();

    /**
     * 根据组织需求统计患者数量
     *
     * @return 组织需求与患者数量的映射关系
     */
    Map<String, Integer> countPatientsByOrganNeeded();

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

    /**
     * 导入患者信息
     *
     * @param patientList     患者信息列表
     * @param isUpdateSupport 是否更新已存在的患者信息
     * @param operName        操作人名称
     * @return 导入结果信息
     */
    String importPatient(List<Patient> patientList, Boolean isUpdateSupport, String operName);
}
