package com.fribbleQ.evencity.controller;

import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Employee;
import com.fribbleQ.evencity.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;

    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest Request, @RequestBody Employee employee){
        R<Employee> login = employeeService.login(Request, employee);
        log.info("返回的信息:{}",login);
        return login;
    }
    @PostMapping("/logout")
   public R<String> logout(HttpServletRequest Request){
        Request.getSession().removeAttribute("employee");
        return R.success("logout User");
   }

}
