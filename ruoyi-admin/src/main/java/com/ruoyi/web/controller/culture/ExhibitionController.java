package com.ruoyi.web.controller.culture;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.culture.domain.Exhibition;
import com.ruoyi.culture.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 非遗展览管理 Controller
 *
 * @Author fanjaixing
 * @Date 2024/3/18 21:30
 */
@RestController
@RequestMapping("/culture/exhibition")
@RequiredArgsConstructor
public class ExhibitionController extends BaseController {

    private final ExhibitionService exhibitionService;

    // 获取所有非遗展览列表
    @GetMapping("/listAll")
    public TableDataInfo listAllExhibitions() {
        startPage();
        List<Exhibition> allExhibitions = exhibitionService.getAllExhibitions();
        return getDataTable(allExhibitions);
    }

    // 根据展览ID获取展览信息
    @GetMapping("/detail")
    public AjaxResult getExhibitionById(@RequestParam Long exhibitionId) {
        return AjaxResult.success(exhibitionService.getExhibitionById(exhibitionId));
    }

    // 添加非遗展览
    @PostMapping("/add")
    public AjaxResult addExhibition(@RequestBody Exhibition exhibition) {
        return toAjax(exhibitionService.addExhibition(exhibition));
    }

    // 更新非遗展览信息
    @PostMapping("/update")
    public AjaxResult updateExhibition(@RequestBody Exhibition exhibition) {
        return toAjax(exhibitionService.updateExhibition(exhibition));
    }

    // 删除非遗展览
    @GetMapping("/delete")
    public AjaxResult deleteExhibition(@RequestParam Long exhibitionId) {
        return toAjax(exhibitionService.deleteExhibition(exhibitionId));
    }
}
