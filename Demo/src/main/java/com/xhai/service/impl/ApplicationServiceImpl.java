package com.xhai.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhai.entity.Application;
import com.xhai.mapper.ApplicationMapper;
import com.xhai.service.ApplicationService;
import com.xhai.service.DemandService;
import com.xhai.service.UserService;
import com.xhai.vo.ApplicationVO;
import com.xhai.vo.DemandVO;
import com.xhai.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ApplicationServiceImpl extends ServiceImpl<ApplicationMapper, Application> implements ApplicationService {

    @Autowired
    private UserService userService;

    @Autowired
    private DemandService demandService;

    @Override
    public ApplicationVO createApplication(Application application) {
        save(application);
        return convertToVO(application);
    }

    @Override
    public ApplicationVO updateApplication(Application application) {
        updateById(application);
        return convertToVO(application);
    }

    @Override
    public ApplicationVO getApplicationById(String id) {
        Application application = getById(id);
        return application != null ? convertToVO(application) : null;
    }

    @Override
    public void deleteApplication(String id) {
        removeById(id);
    }

    @Override
    public List<ApplicationVO> getAllApplications() {
        List<Application> applications = list();
        return applications.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationVO> getApplicationsByDemand(String demandId) {
        List<Application> applications = baseMapper.findByDemandId(demandId);
        return applications.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationVO> getApplicationsByApplicant(String applicantId) {
        List<Application> applications = baseMapper.findByApplicantId(applicantId);
        return applications.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<ApplicationVO> getApplicationsByDemandAndStatus(String demandId, String status) {
        List<Application> applications = baseMapper.findByDemandIdAndStatus(demandId, status);
        return applications.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private ApplicationVO convertToVO(Application application) {
        ApplicationVO vo = new ApplicationVO();
        BeanUtils.copyProperties(application, vo);
        
        // 设置申请人信息
        UserVO applicant = userService.getUserById(application.getApplicantId());
        vo.setApplicant(applicant);
        
        // 设置需求信息
        DemandVO demand = demandService.getDemandById(application.getDemandId());
        vo.setDemand(demand);
        
        return vo;
    }
} 