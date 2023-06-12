package com.fribbleQ.evencity.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Employee;
import com.fribbleQ.evencity.mapper.EmployeeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Service
public interface EmployeeService extends IService<Employee> {
    public R<Employee> login(HttpServletRequest Request, Employee employee);
}
