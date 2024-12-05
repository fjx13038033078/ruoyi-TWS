package com.ruoyi.student.service.impl;

import com.ruoyi.student.domain.Grade;
import com.ruoyi.student.mapper.GradeMapper;
import com.ruoyi.student.service.GradeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author 范佳兴
 * @date 2024/12/5 9:58
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class GradeServiceImpl implements GradeService {

    private final GradeMapper gradeMapper;
    @Override
    public List<Grade> getAllGrades() {
        return gradeMapper.getAllGrades();
    }

    @Override
    public Grade getGradeById(Long gradeId) {
        return gradeMapper.getGradeById(gradeId);
    }

    @Override
    public boolean addGrade(Grade grade) {
        int rows = gradeMapper.addGrade(grade);
        return rows > 0;
    }

    @Override
    public boolean updateGrade(Grade grade) {
        int rows = gradeMapper.updateGrade(grade);
        return rows > 0;
    }

    @Override
    public boolean deleteGrade(Long gradeId) {
        int rows = gradeMapper.deleteGrade(gradeId);
        return rows > 0;
    }
}
