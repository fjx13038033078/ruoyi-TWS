package com.ruoyi.web.controller.culture;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.culture.domain.Culture;
import com.ruoyi.culture.service.CultureService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 非遗文化管理 Controller
 *
 * @Author fanjaixing
 * @Date 2024/3/18 21:27
 */
@RestController
@RequestMapping("/culture")
@RequiredArgsConstructor
public class CultureController extends BaseController {

    private final CultureService cultureService;

    // 获取所有非遗文化列表
    @GetMapping("/listAll")
    public TableDataInfo listAllCultures() {
        startPage();
        List<Culture> allCultures = cultureService.getAllCultures();
        return getDataTable(allCultures);
    }

    // 根据非遗ID获取非遗文化信息
    @GetMapping("/detail")
    public AjaxResult getCultureById(@RequestParam Long cultureId) {
        return AjaxResult.success(cultureService.getCultureById(cultureId));
    }

    // 添加非遗文化
    @PostMapping("/add")
    public AjaxResult addCulture(@RequestBody Culture culture) {
        return toAjax(cultureService.addCulture(culture));
    }

    // 更新非遗文化信息
    @PostMapping("/update")
    public AjaxResult updateCulture(@RequestBody Culture culture) {
        return toAjax(cultureService.updateCulture(culture));
    }

    // 删除非遗文化
    @DeleteMapping("/delete")
    public AjaxResult deleteCulture(@RequestParam Long cultureId) {
        return toAjax(cultureService.deleteCulture(cultureId));
    }
}
