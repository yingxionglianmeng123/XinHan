package com.xhai.controller;

import com.xhai.entity.Application;
import com.xhai.service.ApplicationService;
import com.xhai.vo.ApiResponse;
import com.xhai.vo.ApplicationVO;
import com.xhai.vo.ResultCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApiResponse<ApplicationVO>> createApplication(@RequestBody Application application) {
        try {
            return ResponseEntity.ok(ApiResponse.success(applicationService.createApplication(application)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ApiResponse<ApplicationVO>> updateApplication(@PathVariable String id, @RequestBody Application application) {
        try {
            application.setId(id);
            return ResponseEntity.ok(ApiResponse.success(applicationService.updateApplication(application)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/detail/{id}")
    public ResponseEntity<ApiResponse<ApplicationVO>> getApplication(@PathVariable String id) {
        ApplicationVO application = applicationService.getApplicationById(id);
        return application != null ? 
            ResponseEntity.ok(ApiResponse.success(application)) : 
            ResponseEntity.ok(ApiResponse.error(ResultCode.NOT_FOUND, "申请不存在"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteApplication(@PathVariable String id) {
        try {
            applicationService.deleteApplication(id);
            return ResponseEntity.ok(ApiResponse.success(null));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplicationVO>>> getAllApplications() {
        try {
            return ResponseEntity.ok(ApiResponse.success(applicationService.getAllApplications()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(ApiResponse.error(ResultCode.INTERNAL_ERROR, e.getMessage()));
        }
    }

    @GetMapping("/demand/{demandId}")
    public ResponseEntity<ApiResponse<List<ApplicationVO>>> getApplicationsByDemand(@PathVariable String demandId) {
        try {
            return ResponseEntity.ok(ApiResponse.success(applicationService.getApplicationsByDemand(demandId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/applicant/{applicantId}")
    public ResponseEntity<ApiResponse<List<ApplicationVO>>> getApplicationsByApplicant(@PathVariable String applicantId) {
        try {
            return ResponseEntity.ok(ApiResponse.success(applicationService.getApplicationsByApplicant(applicantId)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }

    @GetMapping("/demand/{demandId}/status/{status}")
    public ResponseEntity<ApiResponse<List<ApplicationVO>>> getApplicationsByDemandAndStatus(
            @PathVariable String demandId,
            @PathVariable String status) {
        try {
            return ResponseEntity.ok(ApiResponse.success(applicationService.getApplicationsByDemandAndStatus(demandId, status)));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(ApiResponse.error(ResultCode.BAD_REQUEST, e.getMessage()));
        }
    }
} 