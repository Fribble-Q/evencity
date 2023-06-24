package com.fribbleQ.evencity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fribbleQ.evencity.DTO.DishDto;
import com.fribbleQ.evencity.common.BaseThreadLocal;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Dish;
import com.fribbleQ.evencity.entity.Employee;
import com.fribbleQ.evencity.service.DishFlavorService;
import com.fribbleQ.evencity.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/dish")
public class DishController {

    @Autowired
    private DishService dishService;

    @PostMapping
    public R<String> AddCategory(@RequestBody DishDto dishDto, HttpServletRequest request){

        Employee emp = (Employee) request.getSession().getAttribute("employee");
        Long id = emp.getId();
        BaseThreadLocal.setBase(id);
        R r = dishService.InserOfdishDto(dishDto);
        return r;
    }
    @GetMapping("/page")
    public R Page(int page,int pageSize,String name){
        Page<DishDto> page1 = dishService.PageModule(page, pageSize, name);
        return new R(page1,1);
    }

    @GetMapping("{id}")
    public R DishById(@PathVariable Long id){
        DishDto dishDto =dishService.SeletcById(id);
        log.info("dishDto:{}",dishDto);
        return new R(dishDto,1);
    }
    @PutMapping
    public R<String> UpdateById(@RequestBody DishDto dishDto, HttpServletRequest request){
        Employee emp = (Employee) request.getSession().getAttribute("employee");
        Long id = emp.getId();
        BaseThreadLocal.setBase(id);
        dishService.UpdateOfDishDto(dishDto);
        return R.success("添加成功");
    }
    @GetMapping("/list")
    public R<List<Dish>> list(Dish dish){
         Long id = dish.getCategoryId();
         List<Dish> list = dishService.getByCategoryId(id);
         return R.success(list);
    }



}
