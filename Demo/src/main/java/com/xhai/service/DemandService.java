package com.xhai.service;

import com.xhai.entity.Demand;
import com.xhai.vo.DemandVO;
import com.xhai.vo.DemandStatisticsVO;
import java.util.List;

public interface DemandService {
    // 创建需求
    DemandVO createDemand(Demand demand);
    
    // 更新需求
    DemandVO updateDemand(Demand demand);
    
    // 获取需求详情
    DemandVO getDemandById(String id);
    
    // 获取需求列表
    List<DemandVO> getDemands(String category, String status, String publisherId, Integer page, Integer size);
    
    // 删除需求
    void deleteDemand(String id);
    
    // 更新需求状态
    DemandVO updateStatus(String id, String status);
    
    // 增加浏览量
    void incrementViewCount(String id);
    
    // 根据状态获取需求
    List<DemandVO> getDemandsByStatus(String status);
    
    // 获取需求统计信息
    DemandStatisticsVO getDemandStatistics();
} 