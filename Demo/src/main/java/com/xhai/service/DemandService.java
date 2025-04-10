package com.xhai.service;

import com.xhai.entity.Demand;
import com.xhai.vo.DemandVO;
import com.xhai.vo.DemandStatisticsVO;
import com.xhai.vo.PageResponse;

import java.util.List;

public interface DemandService {
    DemandVO createDemand(Demand demand);
    DemandVO updateDemand(Demand demand);
    DemandVO getDemandById(String id);
    void deleteDemand(String id);
    List<DemandVO> getAllDemands();
    List<DemandVO> getDemandsByStatus(String status);
    List<DemandVO> getDemandsByPublisher(String publisherId);
    List<DemandVO> getDemandsBySkill(String skill);
    void incrementViewCount(String id);
    void incrementApplyCount(String id);
    boolean completeDemand(String id);
    DemandStatisticsVO getDemandStatistics();
    List<DemandVO> getLatestDemands(int limit);
} 