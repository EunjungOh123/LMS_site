package com.example.coursesite_final.banner.mapper;

import com.example.coursesite_final.banner.dto.BannerDto;
import com.example.coursesite_final.banner.model.BannerParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface BannerMapper {
    long selectListCount(BannerParam parameter);
    List<BannerDto> selectList(BannerParam parameter);
}
