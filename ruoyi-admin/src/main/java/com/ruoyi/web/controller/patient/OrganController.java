package com.ruoyi.web.controller.patient;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.patient.domain.Organ;
import com.ruoyi.patient.service.OrganService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2025/3/11 10:05
 */
@RestController
@RequestMapping("/organ")
@RequiredArgsConstructor
public class OrganController extends BaseController {
    private final OrganService organService;

    /**
     * 获取所有器官供体列表
     */
    @GetMapping("/listAll")
    public TableDataInfo listAllOrgans() {
        startPage();
        List<Organ> allOrgans = organService.getAllOrgans();
        return getDataTable(allOrgans);
    }

    /**
     * 根据 ID 获取器官详情
     */
    @GetMapping("/detail")
    public AjaxResult getOrganById(@RequestParam Long id) {
        return AjaxResult.success(organService.getOrganById(id));
    }

    /**
     * 添加器官信息
     */
    @PostMapping("/add")
    public AjaxResult addOrgan(@RequestBody Organ organ) {
        return toAjax(organService.addOrgan(organ));
    }

    /**
     * 更新器官信息
     */
    @PostMapping("/update")
    public AjaxResult updateOrgan(@RequestBody Organ organ) {
        return toAjax(organService.updateOrgan(organ));
    }

    /**
     * 删除器官信息
     */
    @GetMapping("/delete")
    public AjaxResult deleteOrgan(@RequestParam Long id) {
        return toAjax(organService.deleteOrgan(id));
    }
}
