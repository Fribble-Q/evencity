package com.fribbleQ.evencity.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Employee;
import com.fribbleQ.evencity.mapper.EmployeeMapper;
import com.fribbleQ.evencity.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Slf4j
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public R<Employee> login(HttpServletRequest request, Employee employee) {
        //获取到密码
        String password = employee.getPassword();

        String s = DigestUtils.md5DigestAsHex(password.getBytes());
        //根据Username查询对象
        LambdaQueryWrapper<Employee> wrapper = new LambdaQueryWrapper<>();

        wrapper.eq(Employee::getUsername,employee.getUsername());
        Employee byId = employeeMapper.selectOne(wrapper);
        log.info("Employee 信息:{}",byId);

        if (byId==null){
            return R.error("login error");
        }else if (!byId.getPassword().equals(s)){
            log.info("byId.getPassword 信息:{}",byId.getPassword());
            return R.error("password error");
        }else if (byId.getStatus().equals(0)){
            log.info("byId.getStatus 信息:{}",byId.getStatus());
            return R.error("Status forbid");
        }else
            request.getSession().setAttribute("employee",byId);
            log.info("RequestSession 信息:{}",request.getSession());

        return R.success(byId);
    }

    @Override
    public void SaveEmployee(HttpServletRequest request,Employee employee) {
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
        employee.setCreateTime(LocalDateTime.now());
        employee.setUpdateTime(LocalDateTime.now());
        Employee emp = (Employee) request.getSession().getAttribute("employee");
        HttpSession session = request.getSession();
        log.info("当前session{}",session);
        Long empId = emp.getId();
        employee.setCreateUser(empId);
        employee.setUpdateUser(empId);
        employeeMapper.insert(employee);
    }


}
