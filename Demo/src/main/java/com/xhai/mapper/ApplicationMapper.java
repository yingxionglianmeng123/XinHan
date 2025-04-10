package com.xhai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhai.entity.Application;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ApplicationMapper extends BaseMapper<Application> {
    @Select("SELECT * FROM applications WHERE demand_id = #{demandId}")
    List<Application> findByDemandId(String demandId);
    
    @Select("SELECT * FROM applications WHERE applicant_id = #{applicantId}")
    List<Application> findByApplicantId(String applicantId);
    
    @Select("SELECT * FROM applications WHERE demand_id = #{demandId} AND status = #{status}")
    List<Application> findByDemandIdAndStatus(String demandId, String status);
} 