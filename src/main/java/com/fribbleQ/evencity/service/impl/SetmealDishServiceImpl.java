package com.fribbleQ.evencity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fribbleQ.evencity.entity.Setmeal;
import com.fribbleQ.evencity.mapper.SetmealDishMapper;
import com.fribbleQ.evencity.service.SetmealDishService;
import org.springframework.stereotype.Service;

@Service
public class SetmealDishServiceImpl extends ServiceImpl<SetmealDishMapper,Setmeal> implements SetmealDishService {
}
