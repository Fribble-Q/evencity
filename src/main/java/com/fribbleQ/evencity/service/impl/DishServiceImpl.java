package com.fribbleQ.evencity.service.impl;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fribbleQ.evencity.DTO.DishDto;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Category;
import com.fribbleQ.evencity.entity.Dish;
import com.fribbleQ.evencity.entity.DishFlavor;
import com.fribbleQ.evencity.mapper.DishMapper;
import com.fribbleQ.evencity.service.CategoryService;
import com.fribbleQ.evencity.service.DishFlavorService;
import com.fribbleQ.evencity.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
@Service
public class DishServiceImpl extends ServiceImpl<DishMapper,Dish> implements DishService {
    @Autowired
    private DishService dishService;
    @Autowired
    private DishFlavorService service;
    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private CategoryService categoryService;



    @Transactional
    @Override
    public R<String> InserOfdishDto(DishDto dishDto) {
        dishService.save(dishDto);
        Long id = dishDto.getId();
        List<DishFlavor> flavors = dishDto.getFlavors();
        flavors.stream().map((Function<DishFlavor, Object>) DishFlavor -> {
            DishFlavor.setDishId(id);
            return DishFlavor;
        }).collect(Collectors.toList());
        log.info("flavors:{}",flavors);
        service.saveBatch(flavors);
        return R.success("插入成功");
    }

    @Override
    public Page<DishDto> PageModule(int page, int pageSize, String name) {
        Page<Dish> page1 = new Page<>(page,pageSize);
        LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.isNotEmpty(name), Dish::getName,name);
        wrapper.orderByDesc(Dish::getUpdateTime);
        dishMapper.selectPage(page1, wrapper);
        Page<DishDto> pageDTO = new Page<>();
        BeanUtils.copyProperties(page1,pageDTO,"records");
        List<Dish> records = page1.getRecords();
        List<DishDto> dtoList = records.stream().map((listDto) -> {
            Long id = listDto.getCategoryId();
            Category byId = categoryService.getById(id);
            DishDto dishDto = new DishDto();
            if (byId != null) {
                String byIdName = byId.getName();
                dishDto.setCategoryName(byIdName);
            }
            BeanUtils.copyProperties(listDto, dishDto);
            return dishDto;
        }).collect(Collectors.toList());
        pageDTO.setRecords(dtoList);

        return pageDTO;
    }

    @Override
    public DishDto SeletcById(Long id) {
        DishDto dishDto = new DishDto();
        Dish dish = dishMapper.selectById(id);
        BeanUtils.copyProperties(dish,dishDto);
        LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(DishFlavor::getDishId,dish.getId());
        List<DishFlavor> list = service.list(wrapper);
        dishDto.setFlavors(list);
        return dishDto;
    }

    @Transactional
    @Override
    public void UpdateOfDishDto(DishDto dishDto) {
        dishService.updateById(dishDto);
         List<DishFlavor> flavors = dishDto.getFlavors();
         LambdaQueryWrapper<DishFlavor> wrapper = new LambdaQueryWrapper<>();
         service.remove(wrapper.eq(DishFlavor::getDishId,dishDto.getId()));
         List<DishFlavor> collect = flavors.stream().map((item) -> {
            item.setDishId(dishDto.getId());
            return item;
        }).collect(Collectors.toList());
        service.saveBatch(collect);
    }

    @Override
    public List<Dish> getByCategoryId(Long id) {
         LambdaQueryWrapper<Dish> wrapper = new LambdaQueryWrapper<>();
         List<Dish> list = dishMapper.selectList
                 (wrapper.eq(Objects.nonNull(id),
                  Dish::getCategoryId,id).
                  eq(Dish::getStatus,1).
                  orderByAsc(Dish::getSort).
                  orderByDesc(Dish::getUpdateTime));
         return list;
    }


    // public DishDto SeletcById(Long id) {
    //     Dish byId = this.getById(id);
    //     DishDto dishDto = EntityUtils.toObj(byId, DishDto::new);
    //     List<DishFlavor> list = service.list(Wrappers.lambdaQuery(DishFlavor.class).eq(DishFlavor::getDishId,byId.getId()));
    //     dishDto.setFlavors(list);
    //     return dishDto;
    // }
}
