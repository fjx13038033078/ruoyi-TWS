package com.ruoyi.culture.service.impl;

import com.ruoyi.culture.domain.Culture;
import com.ruoyi.culture.mapper.CultureMapper;
import com.ruoyi.culture.service.CultureService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 非遗文化管理 Service 实现类
 *
 * @Author fanjaixing
 * @Date 2024/3/18 21:14
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CultureServiceImpl implements CultureService {
    private final CultureMapper cultureMapper;

    /**
     * 获取所有非遗文化
     *
     * @return 所有非遗文化列表
     */
    @Override
    public List<Culture> getAllCultures() {
        return cultureMapper.getAllCultures();
    }

    /**
     * 根据非遗ID获取非遗文化信息
     *
     * @param cultureId 非遗ID
     * @return 非遗文化信息
     */
    @Override
    public Culture getCultureById(Long cultureId) {
        return cultureMapper.getCultureById(cultureId);
    }

    /**
     * 添加非遗文化
     *
     * @param culture 待添加的非遗文化信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addCulture(Culture culture) {
        int rows = cultureMapper.addCulture(culture);
        return rows > 0;
    }

    /**
     * 更新非遗文化信息
     *
     * @param culture 待更新的非遗文化信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateCulture(Culture culture) {
        int rows = cultureMapper.updateCulture(culture);
        return rows > 0;
    }

    /**
     * 删除非遗文化
     *
     * @param cultureId 待删除的非遗文化ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteCulture(Long cultureId) {
        int rows = cultureMapper.deleteCulture(cultureId);
        return rows > 0;
    }
}
