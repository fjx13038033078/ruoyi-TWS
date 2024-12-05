package com.ruoyi.web.controller.student;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.student.domain.Course;
import com.ruoyi.student.service.CourseService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2024/12/5 10:30
 */

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController extends BaseController {
    private final CourseService courseService;

    // 获取所有课程列表
    @GetMapping("/listAll")
    public TableDataInfo listAllCourses() {
        startPage();
        List<Course> allCourses = courseService.getAllCourses();
        return getDataTable(allCourses);
    }

    // 根据课程ID获取课程详情
    @GetMapping("/detail")
    public AjaxResult getCourseById(@RequestParam Long courseId) {
        return AjaxResult.success(courseService.getCourseById(courseId));
    }

    // 添加课程
    @PostMapping("/add")
    public AjaxResult addCourse(@RequestBody Course course) {
        return toAjax(courseService.addCourse(course));
    }

    // 更新课程信息
    @PostMapping("/update")
    public AjaxResult updateCourse(@RequestBody Course course) {
        return toAjax(courseService.updateCourse(course));
    }

    // 删除课程
    @GetMapping("/delete")
    public AjaxResult deleteCourse(@RequestParam Long courseId) {
        return toAjax(courseService.deleteCourse(courseId));
    }
}
