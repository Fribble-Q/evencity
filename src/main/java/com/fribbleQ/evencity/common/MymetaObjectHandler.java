package com.fribbleQ.evencity.common;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class MymetaObjectHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        metaObject.setValue("createTime", LocalDateTime.now());
        metaObject.setValue("createUser", BaseThreadLocal.getBase());
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        long id = Thread.currentThread().getId();
        log.info("MymetaObjectHandler线程 id={}",id);
        metaObject.setValue("updateTime", LocalDateTime.now());
        metaObject.setValue("updateUser",BaseThreadLocal.getBase() );
    }
}
