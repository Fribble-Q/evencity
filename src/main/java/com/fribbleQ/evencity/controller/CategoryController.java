package com.fribbleQ.evencity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fribbleQ.evencity.DTO.DishDto;
import com.fribbleQ.evencity.Exception.DelectDisException;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Category;
import com.fribbleQ.evencity.entity.Dish;
import com.fribbleQ.evencity.entity.Employee;
import com.fribbleQ.evencity.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @PostMapping
    public R CategorySave(HttpServletRequest request,@RequestBody Category category){
        return categoryService.SortSave(request,category);
    }

    @GetMapping("/page")
    public R page(int page,int pageSize,String name){
        Page<Category> module = categoryService.PageModule(page, pageSize, name);
        return new R(module,1);
    }

    @DeleteMapping
    public R delectById( long ids) throws DelectDisException {
        R r = categoryService.removeId(ids);
        return r;
    }

    @PutMapping
    public R UpdateById(@RequestBody Category category){
        R r = categoryService.updateId(category);
        return r;
    }

    @GetMapping("/list")
    public R List(Category category){
        R r = categoryService.SelectByCategory(category);
        return r;
    }



}
