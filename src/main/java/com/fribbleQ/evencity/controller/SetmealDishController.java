package com.fribbleQ.evencity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fribbleQ.evencity.DTO.DishDto;
import com.fribbleQ.evencity.DTO.SetmealDto;
import com.fribbleQ.evencity.Exception.DelectDisException;
import com.fribbleQ.evencity.common.BaseThreadLocal;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Employee;
import com.fribbleQ.evencity.entity.SetmealDish;
import com.fribbleQ.evencity.service.SetmealDService;
import com.fribbleQ.evencity.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/setmeal")
public class SetmealDishController {

    @Autowired
    private SetmealDService setmealDService;

    @PostMapping
    public R<String> SetmealInsert(@RequestBody SetmealDto setmealDto, HttpServletRequest request){
        Employee emp = (Employee) request.getSession().getAttribute("employee");
        BaseThreadLocal.setBase(emp.getId());
        setmealDService.AddSetmeal(setmealDto);
        return R.success("下单成功");
    }

    @GetMapping("/page")
    public R<Page<SetmealDto>>PageOfSetmeal(int page,int pageSize,String name){
        final Page<SetmealDto> dtoPage = setmealDService.PageOfSetmeal(page, pageSize, name);
        return R.success(dtoPage);
    }

    @DeleteMapping
    public R<String> DeleteSetmeal(@RequestParam(value = "ids") List<Long> id) throws DelectDisException {
        log.info("ids信息:{}",id);
        setmealDService.DeleteOfId(id);
        return R.success("删除成功");
    }

    @PostMapping ("/status/0")
    public R<String> SaleStop(@RequestParam List<Long> ids,HttpServletRequest request) throws DelectDisException {
        Employee emp = (Employee) request.getSession().getAttribute("employee");
        BaseThreadLocal.setBase(emp.getId());
        setmealDService.SaleStop(ids);
        return R.success("已经停售");
    }


}
