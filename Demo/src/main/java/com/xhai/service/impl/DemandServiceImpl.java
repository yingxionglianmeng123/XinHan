package com.xhai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xhai.entity.Demand;
import com.xhai.mapper.DemandMapper;
import com.xhai.service.DemandService;
import com.xhai.service.UserService;
import com.xhai.vo.DemandVO;
import com.xhai.vo.DemandStatisticsVO;
import com.xhai.vo.PublisherVO;
import com.xhai.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemandServiceImpl extends ServiceImpl<DemandMapper, Demand> implements DemandService {

    @Autowired
    private DemandMapper demandMapper;

    @Autowired
    private UserService userService;

    @Override
    public DemandVO createDemand(Demand demand) {
        // 设置默认值
        if (demand.getApplicants() == null) {
            demand.setApplicants(new ArrayList<>());
        }
        if (demand.getViewCount() == null) {
            demand.setViewCount(0);
        }
        if (demand.getBlockchainVerified() == null) {
            demand.setBlockchainVerified(false);
        }
        if (demand.getDeleted() == null) {
            demand.setDeleted(0);
        }
        if (demand.getCreatedAt() == null) {
            demand.setCreatedAt(LocalDateTime.now());
        }
        
        save(demand);
        return convertToVO(demand);
    }

    @Override
    public DemandVO updateDemand(Demand demand) {
        updateById(demand);
        return convertToVO(demand);
    }

    @Override
    public DemandVO getDemandById(String id) {
        Demand demand = getById(id);
        return demand != null ? convertToVO(demand) : null;
    }

    @Override
    public void deleteDemand(String id) {
        removeById(id);
    }

    @Override
    public List<DemandVO> getAllDemands() {
        List<Demand> demands = list();
        return demands.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DemandVO> getDemandsByStatus(String status) {
        List<Demand> demands = baseMapper.findByStatus(status);
        return demands.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DemandVO> getDemandsByPublisher(String publisherId) {
        List<Demand> demands = baseMapper.findByPublisherId(publisherId);
        return demands.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public List<DemandVO> getDemandsBySkill(String skill) {
        List<Demand> demands = baseMapper.findBySkill(skill);
        return demands.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void incrementViewCount(String id) {
        Demand demand = getById(id);
        if (demand != null) {
            demand.setViewCount(demand.getViewCount() + 1);
            updateById(demand);
        }
    }

    @Override
    @Transactional
    public void incrementApplyCount(String id) {
        Demand demand = getById(id);
        if (demand != null) {
            // 由于我们不再使用applyCount字段，这个方法可以留空或删除
            // 申请者信息现在存储在applicants列表中
        }
    }

    @Override
    @Transactional
    public boolean completeDemand(String id) {
        Demand demand = getById(id);
        if (demand != null && "open".equals(demand.getStatus())) {
            demand.setStatus("completed");
            return updateById(demand);
        }
        return false;
    }

    @Override
    public DemandStatisticsVO getDemandStatistics() {
        DemandStatisticsVO statistics = new DemandStatisticsVO();
        
        // 获取所有需求
        List<Demand> allDemands = list();
        statistics.setTotalDemands((long) allDemands.size());
        
        // 统计各状态需求数量
        statistics.setPendingDemands(allDemands.stream()
                .filter(d -> "pending".equals(d.getStatus()))
                .count());
        
        statistics.setInProgressDemands(allDemands.stream()
                .filter(d -> "in_progress".equals(d.getStatus()))
                .count());
        
        statistics.setCompletedDemands(allDemands.stream()
                .filter(d -> "completed".equals(d.getStatus()))
                .count());
        
        // 设置钱包余额（这里暂时设置为0，后续可以集成钱包服务）
        statistics.setWalletBalance(0.0);
        
        return statistics;
    }

    @Override
    public List<DemandVO> getLatestDemands(int limit) {
        LambdaQueryWrapper<Demand> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(Demand::getCreatedAt)
               .last("LIMIT " + limit);
        List<Demand> demands = list(wrapper);
        return demands.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private DemandVO convertToVO(Demand demand) {
        DemandVO vo = new DemandVO();
        BeanUtils.copyProperties(demand, vo);
        
        // 设置发布者信息
        if (demand.getPublisherId() != null) {
            PublisherVO publisher = new PublisherVO();
            publisher.setId(demand.getPublisherId());
            
            // 尝试从用户服务获取用户信息
            UserVO user = userService.getUserById(demand.getPublisherId());
            if (user != null) {
                publisher.setName(user.getNickName() != null ? user.getNickName() : demand.getPublisherId());
                publisher.setAvatar(user.getAvatarUrl() != null ? user.getAvatarUrl() : "/images/default-avatar.png");
            } else {
                publisher.setName(demand.getPublisherId());
                publisher.setAvatar("/images/default-avatar.png");
            }
            
            vo.setPublisher(publisher);
        }
        
        return vo;
    }
} 