package com.xhai.controller;
import com.xhai.annotation.RequireToken;
import com.xhai.entity.Demand;
import com.xhai.service.DemandService;
import com.xhai.vo.ApiResponse;
import com.xhai.vo.DemandVO;
import com.xhai.vo.DemandStatisticsVO;
import com.xhai.vo.OperationResult;
import com.xhai.vo.PageResponse;
import com.xhai.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/api/demands")
public class DemandController {

    @Autowired
    private DemandService demandService;
    
    //创建需求
    @PostMapping
    @RequireToken
    public ResponseEntity<ApiResponse<DemandVO>> createDemand(@RequestBody Demand demand) {
        try {
            // 从请求中获取当前用户ID
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                String userId = (String) request.getAttribute("currentUserId");
                if (userId != null) {
                    demand.setPublisherId(userId);
                }
            }
            return ResponseEntity.ok(ApiResponse.success(demandService.createDemand(demand)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }
    
    //更新需求
    @PutMapping("/update")
    @RequireToken
    public ResponseEntity<ApiResponse<DemandVO>> updateDemand(@RequestParam String id, @RequestBody Demand demand) {
        try {
            demand.setId(id);
            return ResponseEntity.ok(ApiResponse.success(demandService.updateDemand(demand)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }
    
    // 获取需求
    @GetMapping("/detail")
    public ResponseEntity<ApiResponse<DemandVO>> getDemand(@RequestParam String id) {
        DemandVO demand = demandService.getDemandById(id);
        return demand != null ? 
            ResponseEntity.ok(ApiResponse.success(demand)) : 
            ResponseEntity.ok(ApiResponse.error(ResultCode.NOT_FOUND, "需求不存在"));
    }
    
    //删除需求
    @DeleteMapping("/delete")
    @RequireToken
    public ResponseEntity<ApiResponse<Void>> deleteDemand(@RequestParam String id) {
        try {
            demandService.deleteDemand(id);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }
    
    //获取所有需求
    @GetMapping
    public ResponseEntity<ApiResponse<PageResponse<DemandVO>>> getAllDemands() {
        try {
            List<DemandVO> demands = demandService.getAllDemands();
            return ResponseEntity.ok(ApiResponse.success(PageResponse.of(demands, (long) demands.size())));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error(ResultCode.INTERNAL_ERROR, e.getMessage()));
        }
    }
    
    //根据状态获取需求
    @GetMapping("/status")
    public ResponseEntity<ApiResponse<PageResponse<DemandVO>>> getDemandsByStatus(@RequestParam String status) {
        try {
            List<DemandVO> demands = demandService.getDemandsByStatus(status);
            return ResponseEntity.ok(ApiResponse.success(PageResponse.of(demands, (long) demands.size())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }
    
    //根据发布者获取需求
    @Transactional
    @GetMapping("/publisher")
    public ResponseEntity<ApiResponse<PageResponse<DemandVO>>> getDemandsByPublisher(@RequestParam String publisherId) {
        try {
            List<DemandVO> demands = demandService.getDemandsByPublisher(publisherId);
            return ResponseEntity.ok(ApiResponse.success(PageResponse.of(demands, (long) demands.size())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }
    
    //根据技能获取需求
    @GetMapping("/skill/{skill}")
    public ResponseEntity<ApiResponse<PageResponse<DemandVO>>> getDemandsBySkill(@PathVariable String skill) {
        try {
            List<DemandVO> demands = demandService.getDemandsBySkill(skill);
            return ResponseEntity.ok(ApiResponse.success(PageResponse.of(demands, (long) demands.size())));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }
    
    //增加浏览量
    @PostMapping("/view")
    public ResponseEntity<ApiResponse<Void>> incrementViewCount(@RequestParam String id) {
        try {
            demandService.incrementViewCount(id);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }
    
    //增加申请量
    @PostMapping("/apply")
    @RequireToken
    public ResponseEntity<ApiResponse<Void>> incrementApplyCount(@RequestParam String id) {
        try {
            demandService.incrementApplyCount(id);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }
    
    //完成需求
    @PostMapping("/complete")
    @RequireToken
    public ResponseEntity<ApiResponse<OperationResult>> completeDemand(@RequestParam String id) {
        try {
            boolean success = demandService.completeDemand(id);
            return ResponseEntity.ok(ApiResponse.success(
                success ? OperationResult.success() : OperationResult.error()
            ));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }

    //获取需求统计信息
    @GetMapping("/statistics")
    public ResponseEntity<ApiResponse<DemandStatisticsVO>> getDemandStatistics() {
        try {
            return ResponseEntity.ok(ApiResponse.success(demandService.getDemandStatistics()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error(ResultCode.INTERNAL_ERROR, e.getMessage()));
        }
    }

    //获取最近5条需求
    @GetMapping("/recent")
    public ResponseEntity<ApiResponse<List<DemandVO>>> getLatestDemands() {
        try {
            return ResponseEntity.ok(ApiResponse.success(demandService.getLatestDemands(5)));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error(ResultCode.INTERNAL_ERROR, e.getMessage()));
        }
    }
} 