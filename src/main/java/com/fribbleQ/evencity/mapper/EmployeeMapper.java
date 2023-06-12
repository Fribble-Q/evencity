package com.fribbleQ.evencity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fribbleQ.evencity.entity.Employee;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
