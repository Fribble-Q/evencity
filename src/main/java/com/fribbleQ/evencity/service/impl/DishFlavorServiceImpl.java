package com.fribbleQ.evencity.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fribbleQ.evencity.entity.DishFlavor;
import com.fribbleQ.evencity.mapper.DishFlavorMapper;
import com.fribbleQ.evencity.service.DishFlavorService;
import org.springframework.stereotype.Service;


@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
