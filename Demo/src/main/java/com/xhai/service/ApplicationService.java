package com.xhai.service;

import com.xhai.entity.Application;
import com.xhai.vo.ApplicationVO;
import java.util.List;

public interface ApplicationService {
    ApplicationVO createApplication(Application application);
    ApplicationVO updateApplication(Application application);
    ApplicationVO getApplicationById(String id);
    void deleteApplication(String id);
    List<ApplicationVO> getAllApplications();
    List<ApplicationVO> getApplicationsByDemand(String demandId);
    List<ApplicationVO> getApplicationsByApplicant(String applicantId);
    List<ApplicationVO> getApplicationsByDemandAndStatus(String demandId, String status);
} 