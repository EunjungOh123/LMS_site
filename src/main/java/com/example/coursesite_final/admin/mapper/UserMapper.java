package com.example.coursesite_final.admin.mapper;

import com.example.coursesite_final.admin.dto.UserDto;
import com.example.coursesite_final.admin.model.UserParam;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    long selectListCount(UserParam parameter);
    List <UserDto> selectList(UserParam parameter);

}
