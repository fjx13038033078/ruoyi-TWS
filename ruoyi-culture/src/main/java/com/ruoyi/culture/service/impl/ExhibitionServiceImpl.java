package com.ruoyi.culture.service.impl;

import com.ruoyi.culture.domain.Exhibition;
import com.ruoyi.culture.mapper.CultureMapper;
import com.ruoyi.culture.mapper.ExhibitionMapper;
import com.ruoyi.culture.service.ExhibitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 非遗展览管理 Service 实现类
 *
 * @Author fanjaixing
 * @Date 2024/3/18 21:16
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExhibitionServiceImpl implements ExhibitionService {

    private final ExhibitionMapper exhibitionMapper;

    private final CultureMapper cultureMapper;

    /**
     * 获取所有非遗展览
     *
     * @return 所有非遗展览列表
     */
    @Override
    public List<Exhibition> getAllExhibitions() {
        List<Exhibition> allExhibitions = exhibitionMapper.getAllExhibitions();
        fillCultureName(allExhibitions);
        return allExhibitions;
    }

    /**
     * 根据展览ID获取展览信息
     *
     * @param exhibitionId 展览ID
     * @return 展览信息
     */
    @Override
    public Exhibition getExhibitionById(Long exhibitionId) {
        return exhibitionMapper.getExhibitionById(exhibitionId);
    }

    /**
     * 添加非遗展览
     *
     * @param exhibition 待添加的非遗展览信息
     * @return 添加成功返回 true，否则返回 false
     */
    @Override
    public boolean addExhibition(Exhibition exhibition) {
        int rows = exhibitionMapper.addExhibition(exhibition);
        return rows > 0;
    }

    /**
     * 更新非遗展览信息
     *
     * @param exhibition 待更新的非遗展览信息
     * @return 更新成功返回 true，否则返回 false
     */
    @Override
    public boolean updateExhibition(Exhibition exhibition) {
        int rows = exhibitionMapper.updateExhibition(exhibition);
        return rows > 0;
    }

    /**
     * 删除非遗展览
     *
     * @param exhibitionId 待删除的非遗展览ID
     * @return 删除成功返回 true，否则返回 false
     */
    @Override
    public boolean deleteExhibition(Long exhibitionId) {
        int rows = exhibitionMapper.deleteExhibition(exhibitionId);
        return rows > 0;
    }

    /**
     * 填充 exhibition 中非遗名称
     *
     * @param exhibitions
     */
    private void fillCultureName(List<Exhibition> exhibitions) {
        for (Exhibition exhibition : exhibitions) {
            Long cultureId = exhibition.getCultureId();
            String cultureName = cultureMapper.getCultureById(cultureId).getCultureName();
            exhibition.setCultureName(cultureName);
        }
    }
}
