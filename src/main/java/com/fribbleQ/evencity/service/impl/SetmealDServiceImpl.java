package com.fribbleQ.evencity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fribbleQ.evencity.DTO.DishDto;
import com.fribbleQ.evencity.DTO.SetmealDto;
import com.fribbleQ.evencity.Exception.DelectDisException;
import com.fribbleQ.evencity.Exception.DelectSetmealExcetion;
import com.fribbleQ.evencity.entity.Category;
import com.fribbleQ.evencity.entity.Dish;
import com.fribbleQ.evencity.entity.Setmeal;
import com.fribbleQ.evencity.entity.SetmealDish;
import com.fribbleQ.evencity.mapper.CategoryMapper;
import com.fribbleQ.evencity.mapper.SetmealDMapper;
import com.fribbleQ.evencity.mapper.SetmealDishMapper;
import com.fribbleQ.evencity.service.SetmealDService;
import com.fribbleQ.evencity.service.SetmealDishService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
public class SetmealDServiceImpl extends ServiceImpl<SetmealDMapper, SetmealDish>  implements SetmealDService {
    @Autowired
    private SetmealDService setmealDService;
    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private SetmealDMapper setmealDMapper;
    @Autowired
    private SetmealDishService setmealDishService;
    @Transactional
    @Override
    public void AddSetmeal(SetmealDto setmealDish) {
         setmealDishMapper.insert(setmealDish);
         List<SetmealDish> list = setmealDish.getSetmealDishes();
         List<SetmealDish> list1 = list.stream().map((item) -> {
            item.setSetmealId(setmealDish.getId());
            return item;
        }).collect(Collectors.toList());

        setmealDService.saveBatch(list1);

    }

    @Override
    public Page<SetmealDto> PageOfSetmeal(int page, int pageSize,String name) {
        Page<Setmeal> page1 = new Page<>(page, pageSize);
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        setmealDishMapper.selectPage(page1, wrapper.eq(Objects.nonNull(name),Setmeal::getName,name).orderByAsc(Setmeal::getCreateTime));
        Page<SetmealDto> pageSDto = new Page<>();
        BeanUtils.copyProperties(page1,pageSDto,"records");
         List<Setmeal> dtos = page1.getRecords();
        final List<SetmealDto> collect = dtos.stream().map((item) -> {
            final Long categoryId = item.getCategoryId();
            final Category category = categoryMapper.selectById(categoryId);

            final SetmealDto dto = new SetmealDto();
            dto.setCategoryName(category.getName());
            BeanUtils.copyProperties(item, dto);
            return dto;
        }).collect(Collectors.toList());
        pageSDto.setRecords(collect);
        return pageSDto;
    }

    @Transactional
    @Override
    public void DeleteOfId(List<Long> id) throws DelectDisException {

        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId,id).eq(Setmeal::getStatus,1);
        if (setmealDishService.count(wrapper)>0) {
            throw new DelectDisException("套餐正在售卖");
        }else {
            setmealDishMapper.delete(wrapper);
            LambdaQueryWrapper<SetmealDish> wrapperDish = new LambdaQueryWrapper<>();
            wrapperDish.eq(SetmealDish::getSetmealId,id);
            setmealDMapper.delete(wrapperDish);
        }
    }

    @Transactional
    @Override
    public void SaleStop(List<Long> ids) throws DelectDisException {
        LambdaQueryWrapper<Setmeal> wrapper = new LambdaQueryWrapper<>();
        wrapper.in(Setmeal::getId,ids).eq(Setmeal::getStatus,0);
        if (setmealDishService.count(wrapper)>0) {
            throw new DelectDisException("套餐已经停售");
        }else {

            List<Setmeal> list = setmealDishMapper.selectList(Wrappers.lambdaQuery(Setmeal.class).in(Setmeal::getId,ids));

            LambdaUpdateWrapper<Setmeal> wrapper1 = new LambdaUpdateWrapper<>();
            wrapper1.in(Setmeal::getId,ids).set(Setmeal::getStatus,0);
            for (Setmeal setmeal : list) {
                setmealDishMapper.update(setmeal,wrapper1);
            }
        }
    }
}
