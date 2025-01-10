package com.ruoyi.web.controller.student;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.student.domain.Grade;
import com.ruoyi.student.service.GradeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2024/12/5 10:31
 */

@RestController
@RequestMapping("/student/grade")
@RequiredArgsConstructor
public class GradeController extends BaseController {
    private final GradeService gradeService;

    // 获取所有成绩列表
    @GetMapping("/listAll")
    public TableDataInfo listAllGrades() {
        startPage();
        List<Grade> allGrades = gradeService.getAllGrades();
        return getDataTable(allGrades);
    }

    // 根据成绩ID获取成绩详情
    @GetMapping("/detail")
    public AjaxResult getGradeById(@RequestParam Long gradeId) {
        return AjaxResult.success(gradeService.getGradeById(gradeId));
    }

    // 获取所有失败率
    @GetMapping("/failureRate")
    public AjaxResult getFailureRateByCourse(){
        return success(gradeService.getFailureRateByCourse());
    }

    // 获取所有平均成绩
    @GetMapping("/averageScore")
    public AjaxResult getAverageScoreByCourse(){
        return success(gradeService.getAverageScoreByCourse());
    }

    // 添加成绩
    @PostMapping("/add")
    public AjaxResult addGrade(@RequestBody Grade grade) {
        return toAjax(gradeService.addGrade(grade));
    }

    // 更新成绩
    @PostMapping("/update")
    public AjaxResult updateGrade(@RequestBody Grade grade) {
        return toAjax(gradeService.updateGrade(grade));
    }

    // 删除成绩
    @GetMapping("/delete")
    public AjaxResult deleteGrade(@RequestParam Long gradeId) {
        return toAjax(gradeService.deleteGrade(gradeId));
    }
}
