package com.fribbleQ.evencity.common;

import com.fribbleQ.evencity.Exception.DelectDisException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public R SqlIntegrityConstraint(SQLIntegrityConstraintViolationException exception){
        log.info("异常信息:{}",exception.getStackTrace());
        return  R.error("Username is exist");
    }
    @ExceptionHandler(DelectDisException.class)
    public R DelectDisConstraint(DelectDisException exception){

        log.info("异常信息:{}",exception.getMessage());
        return  R.error(exception.getMessage());
    }

    @ExceptionHandler(IOException.class)
    public R UploadFile(IOException exception){
        log.info("异常信息:{}",exception.getMessage());
        return  R.error(exception.getMessage());
    }

}
