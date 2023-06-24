package com.fribbleQ.evencity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fribbleQ.evencity.DTO.DishDto;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Dish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface DishService extends IService<Dish> {
    R InserOfdishDto(DishDto dishDto);

    Page<DishDto> PageModule(int page, int pageSize, String name);

    DishDto SeletcById(Long id);

    void UpdateOfDishDto(DishDto dishDto);

    List<Dish> getByCategoryId(Long id);
}
