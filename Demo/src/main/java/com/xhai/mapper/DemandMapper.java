package com.xhai.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xhai.entity.Demand;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface DemandMapper extends BaseMapper<Demand> {
    
    @Select("SELECT * FROM demands WHERE status = #{status}")
    List<Demand> findByStatus(String status);
    
    @Select("SELECT * FROM demands WHERE publisher_id = #{publisherId}")
    List<Demand> findByPublisherId(String publisherId);
    
    @Select("SELECT * FROM demands WHERE skill = #{skill}")
    List<Demand> findBySkill(String skill);
} 