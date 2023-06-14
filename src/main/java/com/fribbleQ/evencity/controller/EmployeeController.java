package com.fribbleQ.evencity.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fribbleQ.evencity.common.R;
import com.fribbleQ.evencity.entity.Employee;
import com.fribbleQ.evencity.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

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

   @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("employee save {}",employee.toString());
        employeeService.SaveEmployee(request,employee);
        return R.success("save employee");
   }

   @GetMapping("/page")
   public R page(int page,int pageSize,String name){
       Page<Employee> module = employeeService.PageModule(page, pageSize, name);
       return new R(module,1);
   }

   @PutMapping
    public R employee(HttpServletRequest request,@RequestBody Employee employee){
       R<String> update = employeeService.EmployeeUpdate(request, employee);
       return update;
   }

   @GetMapping(value = "/{id}")
   public R getByid (@PathVariable long id){
        R<Employee> employeeById  =employeeService.SelectById(id);
        return employeeById;
    }


}
