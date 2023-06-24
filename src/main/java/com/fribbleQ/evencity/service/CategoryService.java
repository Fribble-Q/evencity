package com.fribbleQ.evencity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fribbleQ.evencity.DTO.DishDto;
import com.fribbleQ.evencity.Exception.DelectDisException;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Category;
import com.fribbleQ.evencity.entity.Dish;
import com.fribbleQ.evencity.entity.Employee;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public interface CategoryService extends IService<Category> {

    R SortSave(HttpServletRequest request,Category category);

    Page<Category> PageModule(int page, int pageSize, String name);

    R removeId(long id) throws DelectDisException;

    R updateId(Category category);

    R SelectByCategory(Category category);



}
