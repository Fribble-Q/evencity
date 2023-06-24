package com.fribbleQ.evencity.service;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fribbleQ.evencity.DTO.DishDto;
import com.fribbleQ.evencity.DTO.SetmealDto;
import com.fribbleQ.evencity.Exception.DelectDisException;
import com.fribbleQ.evencity.entity.SetmealDish;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface SetmealDService extends IService<SetmealDish> {
    void AddSetmeal(SetmealDto setmealDish);

    Page<SetmealDto> PageOfSetmeal(int page, int pageSize,String name);

    void DeleteOfId(List<Long> id) throws DelectDisException;


    void SaleStop(List<Long> ids) throws DelectDisException;
}
