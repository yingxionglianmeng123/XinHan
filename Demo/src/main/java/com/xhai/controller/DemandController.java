package com.xhai.controller;

import com.xhai.common.Result;
import com.xhai.entity.Demand;
import com.xhai.service.DemandService;
import com.xhai.vo.DemandVO;
import com.xhai.vo.DemandStatisticsVO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/demands")
public class DemandController {

    private static final Logger logger = LoggerFactory.getLogger(DemandController.class);

    @Autowired
    private DemandService demandService;

    // 创建需求
    @PostMapping
    public Result<DemandVO> createDemand(@RequestBody Demand demand) {
        logger.info("创建需求: {}", demand);
        return Result.success(demandService.createDemand(demand));
    }

    // 更新需求
    @PutMapping("/{id}")
    public Result<DemandVO> updateDemand(@PathVariable String id, @RequestBody Demand demand) {
        logger.info("更新需求, id: {}, demand: {}", id, demand);
        demand.setId(id);
        return Result.success(demandService.updateDemand(demand));
    }

    // 获取需求详情
    @GetMapping
    public Result<DemandVO> getDemand(@RequestParam String id) {
        logger.info("获取需求详情, id: {}", id);
        DemandVO demand = demandService.getDemandById(id);
        if (demand == null) {
            logger.warn("需求不存在, id: {}", id);
            return Result.error(404, "需求不存在");
        }
        return Result.success(demand);
    }

    // 获取需求列表
    @GetMapping("/list")
    public Result<List<DemandVO>> getDemands(
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) String publisherId,
            @RequestParam(defaultValue = "1") Integer page,
            @RequestParam(defaultValue = "10") Integer size) {
        logger.info("获取需求列表, category: {}, status: {}, publisherId: {}, page: {}, size: {}", 
            category, status, publisherId, page, size);
        return Result.success(demandService.getDemands(category, status, publisherId, page, size));
    }

    // 删除需求
    @DeleteMapping("/{id}")
    public Result<Void> deleteDemand(@PathVariable String id) {
        logger.info("删除需求, id: {}", id);
        demandService.deleteDemand(id);
        return Result.success();
    }

    // 更新需求状态
    @PutMapping("/{id}/status")
    public Result<DemandVO> updateStatus(@PathVariable String id, @RequestParam String status) {
        logger.info("更新需求状态, id: {}, status: {}", id, status);
        return Result.success(demandService.updateStatus(id, status));
    }

    // 增加浏览量
    @PostMapping("/view")
    public Result<Void> incrementViewCount(@RequestParam String id) {
        logger.info("增加浏览量, id: {}", id);
        demandService.incrementViewCount(id);
        return Result.success();
    }

    // 根据状态获取需求
    @GetMapping("/status")
    public Result<List<DemandVO>> getDemandsByStatus(@RequestParam String status) {
        logger.info("根据状态获取需求, status: {}", status);
        return Result.success(demandService.getDemandsByStatus(status));
    }

    // 获取需求统计信息
    @GetMapping("/statistics")
    public Result<DemandStatisticsVO> getDemandStatistics() {
        logger.info("获取需求统计信息");
        return Result.success(demandService.getDemandStatistics());
    }
} 