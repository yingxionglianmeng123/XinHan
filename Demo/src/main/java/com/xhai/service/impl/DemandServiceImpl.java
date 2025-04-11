package com.xhai.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class DemandServiceImpl implements DemandService {

    @Autowired
    private DemandMapper demandMapper;
    
    @Autowired
    private UserService userService;

    @Override
    @Transactional
    public DemandVO createDemand(Demand demand) {
        // 设置ID和创建时间
        demand.setId(UUID.randomUUID().toString().replace("-", ""));
        demand.setViewCount(0);
        demand.setStatus("pending");
        demand.setCreatedAt(LocalDateTime.now());  // 设置创建时间
        
        // 保存需求
        demandMapper.insert(demand);
        
        // 转换为VO并返回
        return convertToVO(demand);
    }

    @Override
    @Transactional
    public DemandVO updateDemand(Demand demand) {
        // 更新需求
        demandMapper.updateById(demand);
        
        // 转换为VO并返回
        return convertToVO(demand);
    }

    @Override
    public DemandVO getDemandById(String id) {
        Demand demand = demandMapper.selectById(id);
        if (demand == null) {
            return null;
        }
        return convertToVO(demand);
    }

    @Override
    public List<DemandVO> getDemands(String category, String status, String publisherId, Integer page, Integer size) {
        // 构建查询条件
        LambdaQueryWrapper<Demand> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(category != null, Demand::getCategory, category)
               .eq(status != null, Demand::getStatus, status)
               .eq(publisherId != null, Demand::getPublisherId, publisherId)
               .orderByDesc(Demand::getCreatedAt);
        
        // 分页查询
        Page<Demand> pageResult = demandMapper.selectPage(new Page<>(page, size), wrapper);
        
        // 转换为VO并返回
        return pageResult.getRecords().stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public void deleteDemand(String id) {
        demandMapper.deleteById(id);
    }

    @Override
    @Transactional
    public DemandVO updateStatus(String id, String status) {
        Demand demand = demandMapper.selectById(id);
        if (demand != null) {
            demand.setStatus(status);
            demandMapper.updateById(demand);
            return convertToVO(demand);
        }
        return null;
    }

    @Override
    @Transactional
    public void incrementViewCount(String id) {
        Demand demand = demandMapper.selectById(id);
        if (demand != null) {
            demand.setViewCount(demand.getViewCount() + 1);
            demandMapper.updateById(demand);
        }
    }

    @Override
    public List<DemandVO> getDemandsByStatus(String status) {
        LambdaQueryWrapper<Demand> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Demand::getStatus, status)
               .orderByDesc(Demand::getCreatedAt);
        List<Demand> demands = demandMapper.selectList(wrapper);
        return demands.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    @Override
    public DemandStatisticsVO getDemandStatistics() {
        DemandStatisticsVO statistics = new DemandStatisticsVO();
        
        // 获取所有需求
        List<Demand> allDemands = demandMapper.selectList(null);
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
        
        // 计算总浏览量
        statistics.setTotalViews(allDemands.stream()
                .mapToInt(Demand::getViewCount)
                .sum());
        
        return statistics;
    }

    private DemandVO convertToVO(Demand demand) {
        if (demand == null) {
            return null;
        }
        
        DemandVO vo = new DemandVO();
        BeanUtils.copyProperties(demand, vo);
        
        // 设置发布者信息
        if (demand.getPublisherId() != null) {
            PublisherVO publisher = new PublisherVO();
            publisher.setId(demand.getPublisherId());
            // 获取用户信息
            UserVO user = userService.getUserById(demand.getPublisherId());
            if (user != null) {
                publisher.setName(user.getNickName());
                publisher.setAvatar(user.getAvatarUrl());
            } else {
                publisher.setName(demand.getPublisherId());
                publisher.setAvatar("https://example.com/default-avatar.png");
            }
            vo.setPublisher(publisher);
        }
        
        return vo;
    }
} 