package com.fribbleQ.evencity.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Employee;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;


@Service
public interface EmployeeService extends IService<Employee> {
     R<Employee> login(HttpServletRequest Request, Employee employee);
     void SaveEmployee(HttpServletRequest request,Employee employee);
     Page<Employee> PageModule(int page, int pageSize, String name);
     R<String> EmployeeUpdate(HttpServletRequest request,Employee employee);
     R SelectById(long id);
}
