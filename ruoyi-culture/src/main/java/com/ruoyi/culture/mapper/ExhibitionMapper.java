package com.ruoyi.culture.mapper;

import com.ruoyi.culture.domain.Exhibition;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 非遗展览管理 Mapper 接口
 *
 * @Author fanjaixing
 * @Date 2024/3/18 21:03
 */
@Mapper
public interface ExhibitionMapper {
    /**
     * 获取所有非遗展览
     *
     * @return 所有非遗展览列表
     */
    List<Exhibition> getAllExhibitions();

    /**
     * 根据展览ID获取展览信息
     *
     * @param exhibitionId 展览ID
     * @return 展览信息
     */
    Exhibition getExhibitionById(Long exhibitionId);

    /**
     * 添加非遗展览
     *
     * @param exhibition 待添加的非遗展览信息
     * @return 添加成功返回影响的行数，否则返回0
     */
    int addExhibition(Exhibition exhibition);

    /**
     * 更新非遗展览信息
     *
     * @param exhibition 待更新的非遗展览信息
     * @return 更新成功返回影响的行数，否则返回0
     */
    int updateExhibition(Exhibition exhibition);

    /**
     * 删除非遗展览
     *
     * @param exhibitionId 待删除的非遗展览ID
     * @return 删除成功返回影响的行数，否则返回0
     */
    int deleteExhibition(Long exhibitionId);

    /**
     * 根据非遗ID统计展览个数
     *
     * @param cultureId 非遗ID
     * @return 展览个数
     */
    int countByCultureId(Long cultureId);

    /**
     * 删除非遗文化对应的所有展览
     *
     * @param cultureId
     * @return
     */
    int deleteExhibitionByCultureId(Long cultureId);
}
