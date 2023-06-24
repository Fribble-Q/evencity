package com.fribbleQ.evencity.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fribbleQ.evencity.DTO.DishDto;
import com.fribbleQ.evencity.Exception.DelectDisException;
import com.fribbleQ.evencity.common.BaseThreadLocal;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Category;
import com.fribbleQ.evencity.entity.Dish;
import com.fribbleQ.evencity.entity.Employee;
import com.fribbleQ.evencity.entity.Setmeal;
import com.fribbleQ.evencity.mapper.CategoryMapper;
import com.fribbleQ.evencity.mapper.DishMapper;
import com.fribbleQ.evencity.mapper.EmployeeMapper;
import com.fribbleQ.evencity.mapper.SetmealDishMapper;
import com.fribbleQ.evencity.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
public class CategoryServiceImpl  extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
    @Autowired
    private CategoryMapper categoryMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private SetmealDishMapper setmealDishMapper;

    @Override
    public R SortSave(HttpServletRequest request,Category category) {
        Employee emp = (Employee) request.getSession().getAttribute("employee");
        BaseThreadLocal.setBase(emp.getId());
        int insert = categoryMapper.insert(category);
        if (insert==0){
            return R.error("添加失败");
        }else
        return R.success("添加成功");
    }

    @Override
    public Page<Category> PageModule(int page, int pageSize, String name) {
        Page<Category> page1 = new Page<>(page, pageSize);
        categoryMapper.selectPage(page1,null);
        return page1;
    }

    @Override
    public R removeId(long id) throws DelectDisException {
        int i=0;
        LambdaQueryWrapper<Dish> Dishwrapper = new LambdaQueryWrapper<>();
        Dishwrapper.eq(Dish::getCategoryId,id);
        LambdaQueryWrapper<Setmeal> Setmealwrapper = new LambdaQueryWrapper<>();
        Setmealwrapper.eq(Setmeal::getCategoryId, id);

        Integer count = dishMapper.selectCount(Dishwrapper);
        Integer integer = setmealDishMapper.selectCount(Setmealwrapper);
        if (count>0){
            throw new DelectDisException("关联其他对象，不能删除");
        }else if (integer >0){
            throw new DelectDisException("关联菜单其他对象，不能删除");
        }else {
             i = setmealDishMapper.deleteById(id);
        }
        if (i==0){
            return R.error("删除失败");
        }else
            return R.success("删除成功");
    }

    @Override
    public R updateId(Category category) {
        Long id = category.getId();
        BaseThreadLocal.setBase(id);
        int i = categoryMapper.updateById(category);
        if (i>0){
            return R.success("修改成功");
        }else
        return R.error("修改失败");
    }

    @Override
    public R SelectByCategory(Category category) {

        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(category.getType()!=null,Category::getType,category.getType());

        List<Category> list = categoryMapper.selectList(wrapper);

        return new R(list,1);
    }




}
