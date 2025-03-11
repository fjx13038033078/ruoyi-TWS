package com.ruoyi.patient.mapper;

import com.ruoyi.patient.domain.Patient;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/3/11 9:51
 */
@Mapper
public interface PatientMapper {
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
     * @return 影响行数
     */
    int addPatient(Patient patient);

    /**
     * 更新患者登记信息
     *
     * @param patient 患者对象
     * @return 影响行数
     */
    int updatePatient(Patient patient);

    /**
     * 删除患者登记信息
     *
     * @param id 患者 ID
     * @return 影响行数
     */
    int deletePatient(Long id);
}
