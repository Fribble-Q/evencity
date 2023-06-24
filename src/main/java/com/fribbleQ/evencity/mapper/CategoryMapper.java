package com.fribbleQ.evencity.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fribbleQ.evencity.DTO.DishDto;
import com.fribbleQ.evencity.entity.Category;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CategoryMapper extends BaseMapper<Category> {
}
