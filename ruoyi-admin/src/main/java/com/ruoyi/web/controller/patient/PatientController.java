package com.ruoyi.web.controller.patient;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.patient.domain.Patient;
import com.ruoyi.patient.service.PatientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 大黄蜂
 * @date 2025/3/11 9:57
 */
@RestController
@RequestMapping("/patient")
@RequiredArgsConstructor
@Slf4j
public class PatientController extends BaseController {
    private final PatientService patientService;

    /**
     * 获取所有患者登记列表
     *
     * @return 患者登记列表
     */
    @GetMapping("/listAll")
    public TableDataInfo listAllPatients(Patient patient) {
        startPage();
        List<Patient> allPatients = patientService.getAllPatients(patient);
        return getDataTable(allPatients);
    }

    /**
     * 根据患者 ID 获取患者详情
     *
     * @param patientId 患者 ID
     * @return 患者信息
     */
    @GetMapping("/detail")
    public AjaxResult getPatientById(@RequestParam Long patientId) {
        return AjaxResult.success(patientService.getPatientById(patientId));
    }

    /**
     * 获取紧急患者数量
     *
     * @return 紧急患者数量
     */
    @GetMapping("/countEmergencyPatients")
    public AjaxResult countEmergencyPatients() {
        return AjaxResult.success(patientService.countEmergencyPatients());
    }

    @GetMapping("/countPatientsByOrganNeeded")
    public AjaxResult countPatientsByOrganNeeded() {
        return AjaxResult.success(patientService.countPatientsByOrganNeeded());
    }

    /**
     * 添加患者登记信息
     *
     * @param patient 患者对象
     * @return 操作结果
     */
    @PostMapping("/add")
    public AjaxResult addPatient(@RequestBody Patient patient) {
        return toAjax(patientService.addPatient(patient));
    }

    /**
     * 更新患者登记信息
     *
     * @param patient 患者对象
     * @return 操作结果
     */
    @PostMapping("/update")
    public AjaxResult updatePatient(@RequestBody Patient patient) {
        return toAjax(patientService.updatePatient(patient));
    }

    /**
     * 删除患者登记信息
     *
     * @param patientId 患者 ID
     * @return 操作结果
     */
    @GetMapping("/delete")
    public AjaxResult deletePatient(@RequestParam Long patientId) {
        return toAjax(patientService.deletePatient(patientId));
    }
}
